package com.gamma.tools;

import com.gamma.common.TypeConstants;
import com.gamma.service.entity.GeneratorTableColumnEntity;
import com.gamma.service.entity.GeneratorTableInfoEntity;
import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.util.*;

public class VelocityTools {

    /** 项目空间路径 */
    public static final String PROJECT_PATH = "main/java";

    /** mybatis空间路径 */
    public static final String MYBATIS_PATH = "main/resources/mybatis";


    /**
     * UTF-8 字符集
     */
    public static final String UTF8 = "UTF-8";

    /**
     * 初始化vm方法
     */
    public static void initVelocity()
    {
        Properties p = new Properties();
        try
        {
            // 加载classpath目录下的vm文件
            p.setProperty("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
            // 定义字符集
            p.setProperty(Velocity.ENCODING_DEFAULT, UTF8);
            p.setProperty(Velocity.OUTPUT_ENCODING,  UTF8);
            // 初始化Velocity引擎，指定配置Properties
            Velocity.init(p);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }


    /**
     * 根据配置信息生成模版对象
     * @param entity
     * @return
     */
    public static VelocityContext prepareContext(GeneratorTableInfoEntity entity) {

        String moduleName = entity.getModuleName();
        String businessName = entity.getBusinessName();
        String packageName = entity.getPackageName();
        String functionName = entity.getFunctionName();
        String ClazzName = entity.getClassName();

        String lowNameClassName = StringUtils.uncapitalize(ClazzName);
        String serviceClassName = ClazzName + "Service";

        VelocityContext velocityContext = new VelocityContext();
        velocityContext.put("tableName", entity.getTableName());
        velocityContext.put("functionName", StringUtils.isNotEmpty(functionName) ? functionName : "【请填写功能名称】");
        velocityContext.put("className", lowNameClassName);
        velocityContext.put("ClassName", ClazzName);
        velocityContext.put("domainClassName", ClazzName);
        velocityContext.put("serviceClassName", serviceClassName);


        if(lowNameClassName.contains(entity.getPrototypeClassSuffix())){
            velocityContext.put("prototypeClassName", ClazzName);

        }else {
            velocityContext.put("prototypeClassName", ClazzName + entity.getPrototypeClassSuffix());
        }

        velocityContext.put("modelPackageName", StringUtils.uncapitalize(entity.getPrototypeClassSuffix()));

        velocityContext.put("daoClassName", ClazzName + entity.getDataBaseSuffix());

        velocityContext.put("daoPackageName", StringUtils.uncapitalize(entity.getDataBaseSuffix()));


        velocityContext.put("moduleName", moduleName);
        velocityContext.put("BusinessName", StringUtils.capitalize(entity.getBusinessName()) );
        velocityContext.put("businessName", businessName);
        velocityContext.put("basePackage", entity.getPackagePrefix());
        velocityContext.put("packageName", packageName+"."+moduleName);
        velocityContext.put("author", entity.getFunctionAuthor());
        velocityContext.put("datetime", DateTools.getDate());
        velocityContext.put("pkColumn", entity.getPkColumn());
        velocityContext.put("importList", entity.getImportList());
        velocityContext.put("columns", entity.getColumnList());
        velocityContext.put("table", entity);
        return velocityContext;

    }




    /**
     * 原型类加载配置
     *
     */
    public static String[][] modelClassPathConfig = {{"vm/java/model.java.vm" , "%s/%s/%s.java"}};
    /**
     * 数据库加载配置
     */
    public static String[][] databaseMapperPathConfig = {{"vm/java/mapper.java.vm" , "%s/%s/%s.java"}};

    /**
     * 加载模板路径，及代码文件路径
     *  暂时加 {"vm/sql/sql.vm", "%sMenu.sql"}
     */
    public static String[][] templateJavaPathConfig = {
//            {"vm/java/domain.java.vm" , "%s/domain/%s.java"},
            {"vm/java/service.java.vm", "%s/service/%sService.java"},
            {"vm/java/serviceImpl.java.vm", "%s/service/Impl/%sServiceImpl.java"},
            {"vm/java/controller.java.vm", "%s/controller/%sController.java"},
    };

    /**
     * 加载Mapper模板路径，及代码文件路径
     */
    public static String[][] templateMapperPathConfig = {
            {"vm/xml/mapper.xml.vm", "%s/%s.xml"}
    };
    /**
     * 加载前端代码模板路径，及代码文件路径
     */
    public static String[][] templateVuePathConfig = {
//            {"vm/js/api.js.vm", "%s/api/%s/%s.js"},
            {"vm/vue/index.vue.vm", "%s/views/%s/%s/index.vue"}
    };


    public static Template getTemplate(String template) {
        return Velocity.getTemplate(template, UTF8);
    }

}


