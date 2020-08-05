package com.gamma.service.table.impl;

import com.gamma.base.BaseService;
import com.gamma.bean.table.DatabaseBean;
import com.gamma.bean.table.TableDetailBean;
import com.gamma.common.TypeConstants;
import com.gamma.service.entity.GeneratorTableColumnEntity;
import com.gamma.service.entity.GeneratorTableInfoEntity;
import com.gamma.service.table.GeneratorService;
import com.gamma.tools.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


/***
 *  使用模板生成代码
 * v2
 */

@Service
@Slf4j
public class GeneratorServiceImpl  extends BaseService implements GeneratorService {

    //基础模块名称
    @Value("${generateConfig.basePackage}")
    private String  basePackage;


    //基础模块名称
    @Value("${generateConfig.baseModelName}")
    private String  baseModelName;



    @Override
    public List getList() {
        List<GeneratorTableInfoEntity>  list =  JdbcUtil.getList(GeneratorTableInfoEntity.class);
        return list;
    }

    @Override
    public List<GeneratorTableInfoEntity> connectTargetDataBase(DatabaseBean bean) {
        Connection connection = null;
        List<GeneratorTableInfoEntity> resultList = new LinkedList<>();
        try {
            connection =  DataSourceHelper.connectToDatabase(bean);
            PreparedStatement state = connection.prepareStatement("show tables");
            ResultSet rs = state.executeQuery();
            while(rs.next()){
                GeneratorTableInfoEntity tableDetail = new GeneratorTableInfoEntity();
                String tableName = rs.getString(1);
                tableDetail.setTableName(tableName);
                this.setTableRemark(connection, tableDetail);
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


    @Override
    public void connectAndImport(DatabaseBean bean) {

        Connection connection = null;
        PreparedStatement pstate = null;
        try {
            connection =  DataSourceHelper.connectToDatabase(bean);
            String[] tableNameArr = bean.getTableNameStr().split(",");
            for(String tableName : tableNameArr){
                GeneratorTableInfoEntity tableDetail = new GeneratorTableInfoEntity();
                tableDetail.setTableName(tableName);
                //设置remark 和function
                this.setTableRemark(connection, tableDetail);
                String ClazzName = ServiceCodeGeneratorUtil.changeTableName(tableName);
                String businessName = ServiceCodeGeneratorUtil.getBusinessName(tableName);
                tableDetail.setGeneratId(IdGenerator.simpleUUID());
                tableDetail.setClassName(ClazzName);
                tableDetail.setBusinessName(businessName);
                tableDetail.setPackageName(basePackage);
                tableDetail.setModuleName(baseModelName);
                tableDetail.setCreateTime(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
                JdbcUtil.insert(tableDetail);

                String sql =  "SELECT column_name, (case when (is_nullable = 'no' && column_key != 'PRI') then '1' else null end) as is_required," +
                        "(case when column_key = 'PRI' then '1' else '0' end) as is_pk, ordinal_position as sort," +
                        " column_comment, (case when extra = 'auto_increment' then '1' else '0' end) as is_increment, column_type" +
                        " FROM information_schema.columns" +
                        " WHERE table_schema = (SELECT database()) AND table_name = '"+tableName+"'" +
                        " ORDER BY ordinal_position";

                pstate = connection.prepareStatement(sql);
                ResultSet results = pstate.executeQuery();
                GeneratorTableColumnEntity columnEntity;
                while(results.next()){
                    columnEntity = new GeneratorTableColumnEntity();
                    columnEntity.setColumnId(IdGenerator.simpleUUID());
                    columnEntity.setIsRequired(results.getString("is_required"));
                    columnEntity.setIsPk(results.getString("is_pk"));
                    columnEntity.setSort(results.getString("sort"));
                    columnEntity.setColumnComment(results.getString("column_comment"));
                    columnEntity.setIsIncrement(results.getString("is_increment"));
                    columnEntity.setColumnType(results.getString("column_type"));
                    columnEntity.setTableId(tableDetail.getGeneratId());
                    this.initOtherFiled(columnEntity, results);
                    JdbcUtil.insert(columnEntity);
                }

            }

        }catch (Exception e){
            log.error(e.getMessage());
            e.printStackTrace();

        }finally {
            try {
                if(connection != null){
                    connection.close();
                }
                if(pstate != null){
                    pstate.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    /** 页面不需要显示的列表字段 */
    @Value("#{'${generateConfig.noShowFiled}'.split(',')}")
    private List<String> noShowFiledList;

    private void initOtherFiled(GeneratorTableColumnEntity entity,  ResultSet results) throws SQLException {

        String columnType =  results.getString("column_type");
        String columnName =  results.getString("column_name");
        entity.setColumnName(columnName);
        entity.setJavaField(StringTools.toCamelCase(columnName));
        String typeStr = this.getDbType(columnType);
        if(TypeConstants.COLUMN_TYPE_STR.contains(typeStr)){

            entity.setJavaType(TypeConstants.JAVA_FILED_TYPE_STRING);
            // 字符串长度超过500设置为文本域
            Integer columnLength = this.getColumnLength(columnType);
            String htmlType = columnLength >= 500 ? "textarea" : "input";
            entity.setHtmlType(htmlType);
        }
        else if (TypeConstants.COLUMN_TYPE_TIME.contains(typeStr))
        {
            entity.setJavaType(TypeConstants.JAVA_FILED_TYPE_DATE);
            entity.setHtmlType("datetime");
        }
        else if (TypeConstants.COLUMN_TYPE_NUMBER.contains(typeStr))
        {
            entity.setHtmlType("input");
            // 如果是浮点型 统一用BigDecimal
            String[] str = StringUtils.split(StringUtils.substringBetween(columnType, "(", ")"), ",");
            if (str != null && str.length == 2 && Integer.parseInt(str[1]) > 0)
            {
                entity.setJavaType(TypeConstants.JAVA_FILED_TYPE_BIGDECIMAL);
            }
            // 如果是整形
            else if (str != null && str.length == 1 && Integer.parseInt(str[0]) <= 10)
            {
                entity.setJavaType(TypeConstants.JAVA_FILED_TYPE_INTEGER);
            }
            // 长整形
            else
            {
                entity.setJavaType(TypeConstants.JAVA_FILED_TYPE_LONG);
            }

        }

        if(!noShowFiledList.stream().anyMatch (str -> str.equalsIgnoreCase(columnName)) && !entity.isPrimaryKey()){
            entity.setIsEdit(TypeConstants.REQUIRE);
        }

    }

    /**
     * 获取数据库类型字段
     *
     * @param columnType 列类型
     * @return 截取后的列类型
     */
    private String getDbType(String columnType)
    {
        if (StringUtils.indexOf(columnType, "(") > 0)
        {
            return StringUtils.substringBefore(columnType, "(");
        }
        else
        {
            return columnType;
        }
    }
    /**
     * 获取字段长度
     *
     * @param columnType 列类型
     * @return 截取后的列类型
     */
    private static Integer getColumnLength(String columnType)
    {
        if (StringUtils.indexOf(columnType, "(") > 0)
        {
            String length = StringUtils.substringBetween(columnType, "(", ")");
            return Integer.valueOf(length);
        }
        else
        {
            return 0;
        }
    }

    private void setTableRemark(Connection connection, GeneratorTableInfoEntity tableDetail) throws SQLException {

        DatabaseMetaData dbmd = connection.getMetaData();
        ResultSet tableRet = dbmd.getTables(null, "%",tableDetail.getTableName(),new String[]{"TABLE"});
        while (tableRet.next()) {
            String remark = tableRet.getString("REMARKS");       //表备注
            tableDetail.setRemark(remark);
            tableDetail.setFunctionName(remark);
            break;
        }
    }

    @Override
    public GeneratorTableInfoEntity getTableInfoDetail(String tableName) {
        GeneratorTableInfoEntity entity = new GeneratorTableInfoEntity();
        entity.setTableName(tableName);
        List<GeneratorTableInfoEntity> list = JdbcUtil.queryListByEntity(entity);
        return list.get(0);
    }

    @Override
    public List<GeneratorTableColumnEntity> getTableColumnListById(String generatId) {
        return null;
    }




}
