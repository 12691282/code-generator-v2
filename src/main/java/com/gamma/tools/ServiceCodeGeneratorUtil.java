package com.gamma.tools;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Arrays;

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
	public static void generatorTableToCode(ModelGeneratorConfig config) throws IOException {
		
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


	/**
	 * 更改table的名字
	 * @param tableName
	 * @return
	 */
	public static String changeTableName(String tableName){
		
		StringBuffer sb = new StringBuffer(tableName.length());
		tableName = tableName.toLowerCase();

		String[] tables = tableName.split("_");
		String headStr = tables[0];
		//如果第一段字长小于2，则不取
		if(headStr.length() < 2){
			tables = Arrays.copyOfRange(tables, 1, tables.length);
		}
		String temp;
		for(int i=0;i<tables.length;i++){
			temp = tables[i].trim();
			sb.append(temp.substring(0,1)
				.toUpperCase())
				.append(temp.substring(1));
		}
		
		return sb.toString();
	}

	public static String getBusinessName(String tableName) {
		String[] tables = tableName.split("_");
		return tables[tables.length - 1];
	}


	public static String setLengthByType(String type) {
		if(type.indexOf("(") != -1){
			return type.substring(type.indexOf("(")+1, type.indexOf(")")) ;
		}
		return "";
	}

	
	public static void outputLogger(BufferedWriter bw,String text) throws IOException{
		bw.newLine();
		bw.write("\t\tlog.info("+text+");");
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


	public static void outputAuthorByName(BufferedWriter bw,String className) throws IOException{
		bw.newLine();
		bw.newLine();
		bw.write("/**");bw.newLine();
		bw.write(" * @ClassName "+className);bw.newLine();
		bw.write(" * @Description");bw.newLine();
		bw.write(" * @Author 李强");bw.newLine();
		bw.write(" * @Date "+  DateTime.now().toString("yyyy-MM-dd"));bw.newLine();
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
