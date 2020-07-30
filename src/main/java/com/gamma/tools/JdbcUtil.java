package com.gamma.tools;

import com.gamma.annotation.Column;
import com.gamma.annotation.Table;
import com.gamma.bean.table.DatabaseBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.*;

@Slf4j
@Component
public class JdbcUtil implements InitializingBean {

    private static String username;

    private static String password;

    private static String url;

    private static String driver;

    @Value("${spring.datasource.username}")
    public void setUsername(String username) {
        JdbcUtil.username = username;
    }
    @Value("${spring.datasource.password}")
    public  void setPassword(String password) {
        JdbcUtil.password = password;
    }
    @Value("${spring.datasource.url}")
    public void setUrl(String url) {
        JdbcUtil.url = url;
    }
    @Value("${spring.datasource.driver}")
    public void setDriver(String driver) {
        JdbcUtil.driver = driver;
    }
    private static final DatabaseBean bean =  new DatabaseBean();

    @Override
    public void afterPropertiesSet() {
        bean.setUrl(url);
        bean.setPassword(password);
        bean.setUsername(username);
        bean.setDriver(driver);
    }

    public static <T> List<T> queryList(Class<T> clazz){
        List<T> resultList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstate;
        try {
            connection = DataSourceHelper.connectToDatabase(bean);
            Table table = clazz.getAnnotation(Table.class);
            String tableName = table.value();
            String sqlStart = "SELECT ";
            String sqlEnd = "FROM "+ tableName;
            Field[] filedArr = clazz.getDeclaredFields();
            for (Field f : filedArr) {
                Column column = f.getAnnotation(Column.class);
                sqlStart += " " + column.value()+",";
            }
            sqlStart = sqlStart.substring(0,sqlStart.length()-1);
            String sql = sqlStart + " " + sqlEnd;
            log.info("sql : {}",sql);
            pstate = connection.prepareStatement(sql);
            ResultSet rs = pstate.executeQuery();
            List<String> fieldNameList = getFieldName(filedArr);
            T newClazz;
            while(rs.next()){
                newClazz  = clazz.newInstance();
                for(int i=0,size=fieldNameList.size(); i<size;  i++){
                    String fieldName = getMethodName(fieldNameList.get(i));
                    // 赋值给bean对象对应的值
                    Method get_Method = clazz.getMethod("get" + fieldName);  //获取getMethod方法
                    Method set_Method = clazz.getMethod("set" + fieldName, get_Method.getReturnType());//获得属性set方法
                    Object objValue = getValueByType(get_Method.getReturnType().getName(), rs, i+1);
                    set_Method.invoke(newClazz, objValue);
                }
                resultList.add(newClazz);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if(connection != null){
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return resultList;
    }

    private static Object getValueByType(String name, ResultSet rs, Integer index) throws SQLException {
        if("java.lang.String".equals(name)){
            return rs.getString(index);
        }else if("java.lang.Integer".equals(name)){
            return rs.getInt(index);
        }else if("java.lang.Short".equals(name)){
            return rs.getShort(index);
        }
        return null;
    }


    /**
     * 获取对象中的所有字段名
     *
     * @param cls
     * @return
     */
    private static List<String> getFieldName( Field[] field) {
        List<String> list = new ArrayList<>();
        // 循环此字段数组，获取属性的值
        for (int i = 0; i < field.length && field.length > 0; i++) {
            // 值为 true 则指示反射的对象在使用时应该取消 Java 语言访问检查.
            field[i].setAccessible(true);
            // field[i].getName() 获取此字段的名称
            // field[i].get(object) 获取指定对象上此字段的值
            String name = field[i].getName();
            list.add(name);
        }
        return list;
    }

    // 把一个字符串的第一个字母大写、效率是最高的、
    private static String getMethodName(String filedName) {
        byte[] items = filedName.getBytes();
        items[0] = (byte) ((char) items[0] - 'a' + 'A');
        return new String(items);
    }

    /********************************/

    public static List<Map> connectAndGet(DatabaseBean bean) {
        Connection connection = null;
        PreparedStatement pstate;
        List<Map> resultList = new LinkedList<>();
        try {
            connection =  DataSourceHelper.connectToDatabase(bean);
            pstate = connection.prepareStatement("show tables");
            ResultSet rs = pstate.executeQuery();
            while(rs.next()){
                Map tableDetail = new HashMap();
                String tableName = rs.getString(1);
                tableDetail.put("tableName", tableName);
                DatabaseMetaData dbmd = connection.getMetaData();
                ResultSet tableRet = dbmd.getTables(null, "%",tableName,new String[]{"TABLE"});

                while (tableRet.next()) {
                    String remarks = tableRet.getString("REMARKS");       //表备注
                    log.info(remarks);
                    tableDetail.put("remarks", remarks);
                    break;
                }
                resultList.add(tableDetail);
            }

        }catch (Exception e){
            log.error(e.getMessage());
            e.printStackTrace();
            return null;
        }finally {
            try {
                if(connection != null){
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return resultList;
    }



}
