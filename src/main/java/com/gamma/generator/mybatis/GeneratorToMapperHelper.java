package com.gamma.generator.mybatis;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import com.gamma.bean.code.NameCollectionBean;
import com.gamma.bean.table.TableDetailBean;
import com.gamma.config.ModelGeneratorConfig;
import com.gamma.tools.FileTools;
import com.gamma.tools.ServiceCodeGeneratorUtil;

/**
 * mapper 生成助手
 * @author Administrator
 *
 */
public class GeneratorToMapperHelper {
	
	public static void outputMapper(ModelGeneratorConfig config)throws IOException {
		//xml配置文件生成
		toGeneratorXMLFile(config);
		
	   //mapper文件生成
		toGeneratorMapperClazz(config);
	}
	
	private static void toGeneratorXMLFile(ModelGeneratorConfig config)throws IOException  {
		NameCollectionBean nameBean = config.getNameBean();
		List<TableDetailBean> listBean= nameBean.getListBean();
		
		String fileAddr = config.getPath()+"/"+"mapper/";
		File filePath = new File(fileAddr);
		if(!filePath.exists()){
			filePath.mkdirs();
		}
		String fullBeanPackage =  config.getBeanPackage()+"."+nameBean.getLowerHeadWordClassName()+"."+nameBean.getBeanName();
		String fullModelPackage = config.getModelPackage()+"."+nameBean.getLowerHeadWordClassName()+"."+nameBean.getModelName();
		
		
		BufferedWriter bw = FileTools.writerFiterByUTF8(fileAddr,nameBean.getMapperName());
		
		bw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		bw.newLine();
		bw.write("<!DOCTYPE mapper PUBLIC \" -//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">");
		bw.newLine();
		bw.write("<mapper namespace=\""+config.getMapperPackage()+"."+nameBean.getMapperName()+"\" >");
		bw.newLine();
		bw.write("\t<select id=\""+nameBean.getMapperFindByBeanMethodName()+"\" resultType=\""+fullModelPackage+"\" parameterType=\""+fullBeanPackage+"\">");
		bw.newLine();
		bw.write("\t\tSELECT ");
		for(int i=0,size=listBean.size(); i<size; i++){
			TableDetailBean bean = listBean.get(i);
			if(i>0){
				bw.write(",");
			}
			bw.newLine();
			bw.write("\t\t\t "+bean.getFiled() +" as "+ bean.getCodeField() );
			
		}
		bw.newLine();
		bw.write("\t\tFROM "+nameBean.getTableName());
		bw.newLine();
		bw.write("\t\t<where>");
		bw.newLine();
		for(TableDetailBean bean : listBean){
			if("id".equals(bean.getFiled())){
				continue;
			}
			
			if(bean.isDateTypeField()){
				bw.write("\t\t\t <if test=\""+bean.getCodeFieldForStartTime() +" != null \">");
				bw.newLine();
				bw.write("\t\t\t\t <bind name=\""+bean.getCodeFieldForStartTime() +"_pattern\" value=\""+bean.getCodeFieldForStartTime()+" + ' 00:00:00' \" />");
				bw.newLine();
				bw.write("\t\t\t\t  AND "+bean.getFiled()+" &gt;=  #{"+bean.getCodeFieldForStartTime()+"}");
				bw.newLine();
				bw.write("\t\t\t </if>");
				bw.newLine();
				bw.write("\t\t\t <if test=\""+bean.getCodeFieldForEndTime() +" != null \">");
				bw.newLine();
				bw.write("\t\t\t\t <bind name=\""+bean.getCodeFieldForEndTime() +"_pattern\" value=\""+bean.getCodeFieldForEndTime()+" + ' 23:59:59' \" />");
				bw.newLine();
				bw.write("\t\t\t\t  AND "+bean.getFiled()+" &lt;=  #{"+bean.getCodeFieldForEndTime()+"}");
				bw.newLine();
				bw.write("\t\t\t </if>");
				bw.newLine();
				continue;
			}
			
			bw.write("\t\t\t <if test=\""+bean.getCodeField() +" != null \">");
			bw.newLine();
			bw.write("\t\t\t\t <bind name=\""+bean.getCodeField() +"_pattern\" value=\"'%' + "+bean.getCodeField()+" + '%'\" />");
			bw.newLine();
			bw.write("\t\t\t\t  AND "+bean.getFiled()+" LIKE  #{"+bean.getCodeField()+"_pattern}");
			bw.newLine();
			bw.write("\t\t\t </if>");
			bw.newLine();
		}
		bw.newLine();
		bw.write("\t\t</where>");
		bw.newLine();
		bw.newLine();
		bw.write("\t</select>");
		bw.newLine();
		bw.newLine();
		bw.newLine();
		bw.write("</mapper>");
		bw.newLine();
		bw.flush();
		bw.close();
	}
	
	private static void toGeneratorMapperClazz(ModelGeneratorConfig config) throws IOException {
		NameCollectionBean nameBean = config.getNameBean();
		String pageAddr = config.getMapperPackage();
		String fileAddr = config.getPath()+"/"+pageAddr.replace(".","/")+"/";
		File filePath = new File(fileAddr);
		if(!filePath.exists()){
			filePath.mkdirs();
		}
  
		File serviceImplFile = new File(fileAddr,nameBean.getMapperName()+".java");
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(serviceImplFile)));
		
		bw.write("package " + pageAddr+";");
		bw.newLine();
		bw.newLine();
		bw.newLine();
		bw.write("import java.util.List;"); 
		bw.newLine();
		bw.newLine();
		bw.write("import "+config.getBeanPackage()+"."+nameBean.getLowerHeadWordClassName()+"."+nameBean.getBeanName()+";"); 
		bw.newLine();
		bw.write("import "+config.getModelPackage()+"."+nameBean.getLowerHeadWordClassName()+"."+nameBean.getModelName()+";");
		bw.newLine();
		bw.write("import tk.mybatis.mapper.common.Mapper;");
		bw.newLine();
		ServiceCodeGeneratorUtil.outputAuthor(bw,nameBean.getClazzName()+"  mapper 类");
		bw.newLine();
		bw.write("public interface "+nameBean.getMapperName()+" extends Mapper<"+nameBean.getModelName()+">{");
		bw.newLine();
		bw.newLine();
		bw.newLine();
		bw.newLine();
		bw.write("\t/**"); 
		bw.newLine();
		bw.write("\t/*  根据"+nameBean.getBeanName()+"类模糊查询"); 
		bw.newLine();
		bw.write("\t**/"); 
		bw.newLine();
		bw.write("\tList<"+nameBean.getModelName()+"> "+nameBean.getMapperFindByBeanMethodName()+"("+nameBean.getBeanName()+" bean);"); 
		bw.newLine();
		bw.newLine();
		bw.newLine();
		bw.write("}");
		bw.newLine();
		bw.flush();
		bw.close();
		
	}
}
