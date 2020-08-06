package com.gamma.tools;

import com.gamma.annotation.Column;
import com.gamma.annotation.PrimaryKey;
import com.gamma.annotation.Table;
import com.gamma.bean.DatabaseBean;
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

    public static <T> List<T> getList(Class<T> clazz){
        List<T> resultList = new ArrayList<>();
        String sql = getQuerySql(clazz);
        log.info("sql : {}",sql);
        setAndGetList(clazz, sql, resultList);
        return resultList;
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

    public static void updateById(Object object){

        Connection connection = null;
        try {
            connection = DataSourceHelper.connectToDatabase(bean);
            Class<?> clazz = object.getClass();
            Table table =  clazz.getAnnotation(Table.class);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }finally {
            try {
                if(connection != null){
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public static  String insert(Object object){
        Connection connection = null;
        String key = IdGenerator.simpleUUID();
        try {
            connection = DataSourceHelper.connectToDatabase(bean);
            Class<?> clazz = object.getClass();
            Table table =  clazz.getAnnotation(Table.class);
            String insertColumns = "INSERT INTO " +  table.value() + " (" ;

            String insertValues = ") VALUES (" ;

            Field[] filedArr = clazz.getDeclaredFields();
            for (Field field : filedArr) {
                Column column = field.getAnnotation(Column.class);
                PrimaryKey primaryKey = field.getAnnotation(PrimaryKey.class);

                insertColumns +=  column.value()+",";
                field.setAccessible(true);
                // 获取此字段的名称
                Object value = field.get(object);
                if(primaryKey != null && primaryKey.isKey()){
                    insertValues +=  "'" + key + "',";
                }else if(value != null){
                    insertValues +=  "'" +value + "',";
                }else {
                    insertValues +=   " NULL,";
                }
            }
            insertColumns = insertColumns.substring(0,insertColumns.length()-1);
            insertValues = insertValues.substring(0,insertValues.length()-1) + ")";

            String sql = insertColumns + insertValues;
            log.info(" insert sql : {}",sql);
            connection.setAutoCommit(false);//表示开启事务；
            connection.prepareStatement(sql).execute();
            connection.commit();//表示提交事务；

        } catch (Exception e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }finally {
            try {
                if(connection != null){
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return key;
    }

    /**
     * 根据实体参数读取列表
     * @param entity
     * @param <T>
     * @return
     */
    public static <T> List queryListByEntity(Object entity) {
        List<T> resultList = new ArrayList();
        Class clazz = entity.getClass();
        String sql = getQuerySql(clazz);

        Field[] filedArr = clazz.getDeclaredFields();
        String conditionSql = "";
        int index = 0;
        for (Field field : filedArr) {
            Column column = field.getAnnotation(Column.class);
            field.setAccessible(true);
            // 获取此字段的名称
            Object value = null;
            try {
                value = field.get(entity);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            if(value != null){
                if(index > 0){
                    conditionSql +=  " AND ";
                }
                conditionSql +=  column.value() + " =  '" + value + "'";
                index++;
            }


        }
        if(!conditionSql.isEmpty()){
            sql += " WHERE " + conditionSql;
        }
        log.info("sql : {}",sql);
        setAndGetList(clazz, sql,resultList);
        return resultList;
    }


    private static <T> String getQuerySql(Class<T> clazz) {
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
        return  sqlStart + " " + sqlEnd;
    }


    private static <T> void setAndGetList(Class<T> clazz, String sql, List<T> resultList) {

        Connection connection = null;
        PreparedStatement pstate;
        try {
            connection = DataSourceHelper.connectToDatabase(bean);

            pstate = connection.prepareStatement(sql);
            ResultSet rs = pstate.executeQuery();
            Field[] filedArr = clazz.getDeclaredFields();
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
        log.info("result list size {}", resultList.size());
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


    // 把一个字符串的第一个字母大写、效率是最高的、
    private static String getMethodName(String filedName) {
        byte[] items = filedName.getBytes();
        items[0] = (byte) ((char) items[0] - 'a' + 'A');
        return new String(items);
    }
}
