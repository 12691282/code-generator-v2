package com.gamma.service.table.impl;

import com.gamma.base.BaseService;
import com.gamma.bean.DatabaseBean;
import com.gamma.common.TypeConstants;
import com.gamma.service.entity.GeneratorConfigEntity;
import com.gamma.service.entity.GeneratorTableColumnEntity;
import com.gamma.service.entity.GeneratorTableInfoEntity;
import com.gamma.service.table.GeneratorService;
import com.gamma.tools.*;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.sql.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


/***
 *  使用模板生成代码
 * v2
 */

@Service
@Slf4j
public class GeneratorServiceImpl extends BaseService implements GeneratorService {

    /** 页面不需要显示的列表字段 */
    private List<String> noShowFiledList;

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
                this.setTableComment(connection, tableDetail);
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

            GeneratorConfigEntity config = this.getConfigInfo();
            this.noShowFiledList = Arrays.asList(config.getNoShowFiledList());
            for(String tableName : tableNameArr){
                String lowerName = StringUtils.lowerCase(tableName);
                GeneratorTableInfoEntity tableDetail = new GeneratorTableInfoEntity();
                tableDetail.setTableName(tableName);
                //设置remark 和function
                this.setTableComment(connection, tableDetail);
                String ClazzName = ServiceCodeGeneratorUtil.changeTableName(lowerName);
                String businessName = ServiceCodeGeneratorUtil.getBusinessName(lowerName);
                tableDetail.setClassName(ClazzName);
                tableDetail.setBusinessName(businessName);
                tableDetail.setPrototypeClassSuffix(config.getPrototypeClassSuffix());
                tableDetail.setDataBaseSuffix(config.getDataBaseSuffix());
                tableDetail.setPackageName(config.getBasePackage());
                tableDetail.setModuleName(config.getBaseModelName());
                tableDetail.setFunctionAuthor(config.getAuthorName());
                tableDetail.setCreateTime(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));

                String tdKey = JdbcUtil.insert(tableDetail);

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
                    columnEntity.setIsRequired(results.getString("is_required"));
                    columnEntity.setIsPk(results.getString("is_pk"));
                    columnEntity.setSort(results.getString("sort"));
                    columnEntity.setColumnComment(results.getString("column_comment"));
                    columnEntity.setIsIncrement(results.getString("is_increment"));
                    columnEntity.setColumnType(results.getString("column_type"));
                    columnEntity.setTableId(tdKey);
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

    private void initOtherFiled(GeneratorTableColumnEntity entity,  ResultSet results) throws SQLException {

        String columnType =  results.getString("column_type");
        String columnName =  results.getString("column_name");
        entity.setColumnName(columnName);
        entity.setJavaField(StringTools.toCamelCase(columnName));
        entity.setQueryType("=");//默认等于查询方式
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
            entity.setIsListShow(TypeConstants.REQUIRE);//默认列表展示字段
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

    private void setTableComment(Connection connection, GeneratorTableInfoEntity tableDetail) throws SQLException {

        DatabaseMetaData dbmd = connection.getMetaData();
        ResultSet tableRet = dbmd.getTables(null, "%",tableDetail.getTableName(),new String[]{"TABLE"});
        while (tableRet.next()) {
            String remark = tableRet.getString("REMARKS");       //表备注
            tableDetail.setTableComment(remark);
            tableDetail.setFunctionName(remark);
            break;
        }
    }

    @Override
    public GeneratorTableInfoEntity getTableInfoDetail(String tableName) {
        GeneratorTableInfoEntity entity = new GeneratorTableInfoEntity();
        entity.setTableName(tableName);
        List<GeneratorTableInfoEntity> list = JdbcUtil.queryListByEntity(entity);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<GeneratorTableColumnEntity> getTableColumnListById(String generatId) {
        GeneratorTableColumnEntity entity = new GeneratorTableColumnEntity();
        entity.setTableId(generatId);
        return JdbcUtil.queryListByEntity(entity);
    }

    @Override
    public void updateTableInfoEntity(GeneratorTableInfoEntity entity) {
        JdbcUtil.updateById(entity);
    }

    @Override
    public byte[] getGeneratorByte(String[] tableArr) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);

        //初始化模板
        VelocityTools.initVelocity();

        for (String tableName : tableArr)
        {
           this.toGeneratorCodeZip(tableName, zip);
        }
        IOUtils.closeQuietly(zip);
        return outputStream.toByteArray();
    }

    private void toGeneratorCodeZip(String tableName, ZipOutputStream zip) {
        GeneratorTableInfoEntity entity = this.getTableInfoDetail(tableName);
        if(entity == null){
            return;
        }
        List<GeneratorTableColumnEntity> columnEntityList =  getTableColumnListById(entity.getGeneratId());

        entity.changeColumnToVo(columnEntityList);

        /**
         * 根据配置信息生成模版对象
         */
        VelocityContext context = VelocityTools.prepareContext(entity);

        // 模块名
        String moduleName = entity.getModuleName();
        // 包路径
        String packageName = entity.getPackageName();

        // 大写类名
        String className = entity.getClassName();
        // 业务名称
        String businessName = entity.getBusinessName();

        String javaPath = VelocityTools.PROJECT_PATH + "/" + StringUtils.replace(packageName, ".", "/")+"/"+moduleName;
        String mybatisPath = VelocityTools.MYBATIS_PATH + "/" + moduleName;

        String prototypeClassSuffix = StringUtils.uncapitalize(entity.getPrototypeClassSuffix());
        String modelFileName = className;
        if(!className.contains(entity.getPrototypeClassSuffix())){
            modelFileName += entity.getPrototypeClassSuffix();
        }
        this.toFillContent(VelocityTools.modelClassPathConfig,zip,context,
                javaPath, prototypeClassSuffix, modelFileName);

        String dataBaseSuffix = StringUtils.uncapitalize(entity.getDataBaseSuffix());
        this.toFillContent(VelocityTools.databaseMapperPathConfig,zip,context,
                javaPath, dataBaseSuffix, className+entity.getDataBaseSuffix());

        this.toFillContent(VelocityTools.templateJavaPathConfig,zip,context,javaPath, className);
        this.toFillContent(VelocityTools.templateMapperPathConfig,zip,context,mybatisPath, className);
        this.toFillContent(VelocityTools.templateVuePathConfig,zip,context,"vue", moduleName, businessName);
    }

    private void toFillContent(String[][] templateJavaPathConfig, ZipOutputStream zip, VelocityContext context, String... params) {
        for(String[] templateInfo : templateJavaPathConfig){
            // 渲染模板
            StringWriter sw = new StringWriter();
            String templateNamePath = templateInfo[0];
            Template template = VelocityTools.getTemplate(templateNamePath);
            template.merge(context, sw);
            String fileName = String.format(templateInfo[1], params);
            try
            {
                // 添加到zip
                zip.putNextEntry(new ZipEntry(fileName));
                IOUtils.write(sw.toString(), zip, VelocityTools.UTF8);
                IOUtils.closeQuietly(sw);
                zip.flush();
                zip.closeEntry();
            }
            catch (IOException e)
            {
                e.printStackTrace();
                log.error("渲染模板失败，表名：" + fileName, e);
            }
        }
    }


    @Override
    public GeneratorConfigEntity getConfigInfo() {
        List<GeneratorConfigEntity>  list =  JdbcUtil.getList(GeneratorConfigEntity.class);
        return list.get(0);
    }



}
