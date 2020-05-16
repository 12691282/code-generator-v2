package com.gamma.tools;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.joda.time.DateTime;

import com.gamma.config.ModelGeneratorConfig;
import com.gamma.generator.mybatis.GeneratorToControllerHelper;
import com.gamma.generator.mybatis.GeneratorToEntityClazzHelper;
import com.gamma.generator.mybatis.GeneratorToMapperHelper;
import com.gamma.generator.mybatis.GeneratorToServiceHelper;
/**
 * 服务端代码生成帮助器
 * @author Administrator
 *
 */
public class ServiceCodeGeneratorUtil {
	
	/**
	 * 生成后台代码
	 * @param config
	 * @throws IOException
	 */
	public static void generatorTableToModel(ModelGeneratorConfig config) throws IOException {
		
			
			if(config.getIsBaseBean() != null && config.getIsBaseBean()){
				ServiceCodeGeneratorUtil.outputBaseBean(config);
			}
			if(config.getIsBaseModel() != null && config.getIsBaseModel()){
				ServiceCodeGeneratorUtil.outputBaseModel(config);
			}
			
			GeneratorToControllerHelper.outputController(config);
			
			GeneratorToServiceHelper.outputService(config);
			
			GeneratorToMapperHelper.outputMapper(config);
			
			GeneratorToEntityClazzHelper.outputEntityClazz(config);
		
	}
	

	//转换头字母为小写
	public static String toHeadWordLowerCase(String temp) {
		return temp.substring(0,1).toLowerCase()+temp.substring(1);
	}
	//转换头字母为大写
	public static String toHeadWordUpperCase(String temp) {
		return temp.substring(0,1).toUpperCase()+temp.substring(1);
	}

	private static void outputBaseModel(ModelGeneratorConfig config) throws IOException{
		File beanFile = new File(config.getPath(),"BaseModel.java");
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(beanFile)));
		bw.write("package " + config.getBeanPackage() + ";");
		bw.newLine();
		bw.write("import java.io.Serializable;");
		ServiceCodeGeneratorUtil.outputAuthor(bw,"model父类");
		bw.newLine();
		bw.write("@SuppressWarnings(\"serial\")");
		bw.newLine();
		bw.write("public class BaseModel implements Serializable{");
		bw.newLine();
		bw.write("\t/**主键**/");
		bw.newLine();
		bw.write("\tprivate String id;");
		bw.newLine();
		// 生成get 和 set方法
		
		bw.write("\tpublic String getId(){");
		bw.newLine();
		bw.write("\t\treturn this.id;");
		bw.newLine();
		bw.write("\t}");
		bw.newLine();
		bw.newLine();
		
		bw.write("\tpublic void setId(String id){");
		bw.newLine();
		bw.write("\t\tthis.id;");
		bw.newLine();
		bw.write("\t}");
		bw.newLine();
		bw.newLine();
		
		bw.write("}");
		bw.newLine();
		bw.flush();
		bw.close();
		
	}



	/**
	 * 更改table的名字
	 * @param tableName
	 * @return
	 */
	public static String changeTableName(String tableName){
		
		StringBuffer sb = new StringBuffer(tableName.length());
		tableName = tableName.toLowerCase();
		String[] tables = tableName.split("_");
		String temp = null;
		for(int i=0;i<tables.length;i++){
			temp = tables[i].trim();
			sb.append(temp.substring(0,1)
				.toUpperCase())
				.append(temp.substring(1));
		}
		
		return sb.toString();
	}
	
	

	public static void outputBaseBean( ModelGeneratorConfig config) throws IOException {
		
		File beanFile = new File(config.getPath(),"BaseBean.java");
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(beanFile)));
		bw.write("package " + config.getBeanPackage() + ";");
		bw.newLine();
		bw.write("import java.io.Serializable;");
		ServiceCodeGeneratorUtil.outputAuthor(bw,"排序基类");
		bw.newLine();
		bw.write("@SuppressWarnings(\"serial\")");
		bw.newLine();
		bw.write("public class BaseBean implements Serializable{");
		bw.newLine();
		bw.write("\t/**排序**/");
		bw.newLine();
		bw.write("\tprivate String orderSql;");
		bw.newLine();
		// 生成get 和 set方法
		
		bw.write("\tpublic String getOrderSql(){");
		bw.newLine();
		bw.write("\t\treturn this.orderSql;");
		bw.newLine();
		bw.write("\t}");
		bw.newLine();
		bw.newLine();
		
		bw.write("\tpublic void setOrderSql(String orderSql){");
		bw.newLine();
		bw.write("\t\tthis.orderSql=orderSql;");
		bw.newLine();
		bw.write("\t}");
		bw.newLine();
		bw.newLine();
		
		bw.write("}");
		bw.newLine();
		bw.flush();
		bw.close();
	}
	
	
	public static void outputLogger(BufferedWriter bw,String text) throws IOException{
		bw.newLine();
		bw.write("\t\tlogger.info("+text+");");
		bw.newLine();
	}
	
	public static void outputAuthor(BufferedWriter bw,String text) throws IOException{
		bw.newLine();
		bw.newLine();
		bw.write("/**");bw.newLine();
		bw.write(" *<pre>");bw.newLine();
		bw.write(" *  " + text);bw.newLine();
		bw.write(" *  通过 lion gamma generator 生成，禁止使用商业");bw.newLine();
		bw.write(" *  时间: " +  DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));bw.newLine();
		bw.write(" *</pre>");bw.newLine();
		bw.write(" * @author lion");bw.newLine();
		bw.write(" * @version 1.0");bw.newLine();
		bw.write("**/");
		bw.newLine();
		bw.newLine();
	}
	
	public static void outputHtmlPageAuthor(BufferedWriter bw)throws IOException{
		bw.write("\t\t<div class=\"footer\">");bw.newLine();
		bw.write("\t\t\t<p>&copy; lion 2017</p>");bw.newLine();
		bw.write("\t\t</div>");bw.newLine();
	}



}
