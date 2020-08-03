package com.gamma.service.table.impl;

import com.gamma.base.BaseService;
import com.gamma.bean.table.DatabaseBean;
import com.gamma.bean.table.TableDetailBean;
import com.gamma.service.entity.GeneratorTableColumnEntity;
import com.gamma.service.entity.GeneratorTableInfoEntity;
import com.gamma.service.table.GeneratorService;
import com.gamma.tools.DataSourceHelper;
import com.gamma.tools.JdbcUtil;
import com.gamma.tools.ServiceCodeGeneratorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.*;
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
        List<GeneratorTableInfoEntity>  list =  JdbcUtil.queryList(GeneratorTableInfoEntity.class);
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
                DatabaseMetaData dbmd = connection.getMetaData();
                ResultSet tableRet = dbmd.getTables(null, "%",tableName,new String[]{"TABLE"});

                while (tableRet.next()) {
                    String remark = tableRet.getString("REMARKS");       //表备注
                    tableDetail.setRemark(remark);
                    tableDetail.setFunctionName(remark);
                    break;
                }

                String ClazzName = ServiceCodeGeneratorUtil.changeTableName(tableName);
                String businessName = ServiceCodeGeneratorUtil.getBusinessName(tableName);
                tableDetail.setClassName(ClazzName);
                tableDetail.setBusinessName(businessName);
                tableDetail.setPackageName(basePackage);
                tableDetail.setModuleName(baseModelName);
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
        List<GeneratorTableInfoEntity> infoList = this.connectTargetDataBase(bean);
        Connection connection = null;
        PreparedStatement pstate = null;
        try {
            connection =  DataSourceHelper.connectToDatabase(bean);

            for(GeneratorTableInfoEntity infoEntity : infoList){

                String sql =  "SELECT column_name, (case when (is_nullable = 'no' && column_key != 'PRI') then '1' else null end) as is_required," +
                        "(case when column_key = 'PRI' then '1' else '0' end) as is_pk, ordinal_position as sort," +
                        "column_comment, (case when extra = 'auto_increment' then '1' else '0' end) as is_increment, column_type" +
                        "from information_schema.columns" +
                        "where table_schema = (SELECT database()) and table_name = '"+infoEntity.getTableName()+"'" +
                        "order by ordinal_position";

                pstate = connection.prepareStatement(sql);
                ResultSet results = pstate.executeQuery();
                GeneratorTableColumnEntity columnEntity;
                while(results.next()){
                    columnEntity = new GeneratorTableColumnEntity();
                    String columnName = results.getString("column_name");
                    columnEntity.setColumnName(columnName);


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
}
