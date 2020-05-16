package com.gamma.generator.mybatis;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import org.springframework.util.StringUtils;

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
public class GeneratorToEntityClazzHelper {
	
	private static String type_bigint = "bigint";
	private static String type_char = "char";
	private static String type_date = "date";
	private static String type_int = "int";
	private static String type_float = "float";
	private static String type_text = "text";
	
	public static void outputEntityClazz(ModelGeneratorConfig config)throws IOException {
		//bean文件生成
		toGeneratorBeanEntityClazz(config);
		
	   //model文件生成
		toGeneratorModelEntityClazz(config);
	}
	
	private static void toGeneratorBeanEntityClazz(ModelGeneratorConfig config)throws IOException  {
		NameCollectionBean nameBean = config.getNameBean();
		List<TableDetailBean> listBean= nameBean.getListBean();
		String pageAddr = config.getBeanPackage() + "."+nameBean.getLowerHeadWordClassName();
		String fileAddr = config.getPath()+"/"+pageAddr.replace(".","/")+"/";
		File filePath = new File(fileAddr);
		if(!filePath.exists()){
			filePath.mkdirs();
		}
		
		BufferedWriter bw = FileTools.writerFiterByUTF8(fileAddr,nameBean.getBeanName());
		
		bw.write("package " + pageAddr+";");
		bw.newLine();
		bw.newLine();
		bw.newLine();
		bw.write("import org.springframework.util.StringUtils;"); 
		bw.newLine();
		bw.newLine();
		ServiceCodeGeneratorUtil.outputAuthor(bw,nameBean.getClazzName()+" bean 实体类");
		bw.newLine();
		bw.write("public class "+nameBean.getBeanName()+"{");
		bw.newLine();
		
		for(TableDetailBean tableDetail : listBean){
			
			if(StringUtils.hasText(tableDetail.getComment())){
				bw.write("\t /**" + tableDetail.getComment() + "**/");
				bw.newLine();
			}
			bw.write("\tprivate String " + tableDetail.getCodeField() + ";");
			bw.newLine();
			
			if(tableDetail.isDateTypeField()){
				bw.write("\t /** 查询时间对 **/");
				bw.newLine();
				bw.write("\tprivate String " + tableDetail.getCodeFieldForStartTime() + ";");
				bw.newLine();
				
				bw.write("\tprivate String " + tableDetail.getCodeFieldForEndTime() + ";");
				bw.newLine();
			}
		}
	 
		for(TableDetailBean tableDetail : listBean){
			String tempField =  tableDetail.getCodeField();
			String upTempField = tableDetail.getUpWordField();
			
			bw.newLine();
			bw.write("\tpublic void set" + upTempField + "(String  " + tempField + "){");
			bw.newLine();
			bw.write("\t\tthis." + tempField + " = " + tempField + ";");
			bw.newLine();
			bw.write("\t}");
			bw.newLine();
			
			bw.write("\tpublic String get" + upTempField + "(){");
			bw.newLine();
			bw.write("\t\treturn this."+tempField+";");
			bw.newLine();
			bw.write("\t}");
			bw.newLine();
			
			if(tableDetail.isDateTypeField()){
				tableDetail.toWriteGetAndSetMethodForDateType(bw);
			}
		}
		
		bw.newLine();
		bw.write("\tpublic void "+nameBean.getBeanPrepareDataMethodName()+"() {");
		bw.newLine();

		for(TableDetailBean bean : nameBean.getListBean()){
	
		}
		
		for(TableDetailBean tableDetail : listBean){
			String upTempField = tableDetail.getUpWordField();
			
			bw.write("\t\tif(StringUtils.isEmpty(this.get" + upTempField + "())){");
			bw.newLine();
			bw.write("\t\t\tthis.set" + upTempField + "(null);");
			bw.newLine();
			bw.write("\t\t}");
			bw.newLine();
			
			//判断时间域
			if(tableDetail.isDateTypeField()){
				
				bw.write("\t\tif(StringUtils.isEmpty(this.get"+upTempField+"Start())){");
				bw.newLine();
				bw.write("\t\t\tthis.set"+upTempField+"Start(null);");
				bw.newLine();
				bw.write("\t\t}");
				bw.newLine();
				bw.write("\t\tif(StringUtils.isEmpty(this.get"+upTempField+"End())){");
				bw.newLine();
				bw.write("\t\t\t this.set"+upTempField+"End(null);");
				bw.newLine();
				bw.write("\t\t}");
				bw.newLine();
			}
			
		}
		bw.newLine();
		bw.write("\t}"); 
		bw.newLine();
		//重写toString方法
		generatorToString(bw, nameBean.getBeanName(), listBean);
		
		bw.write("}");
		bw.newLine();
		bw.flush();
		bw.close();
	}
	
	private static void toGeneratorModelEntityClazz(ModelGeneratorConfig config) throws IOException {
		NameCollectionBean nameBean = config.getNameBean();
		List<TableDetailBean> listBean = nameBean.getListBean();
		String pageAddr = config.getModelPackage() + "."+nameBean.getLowerHeadWordClassName();
		String fileAddr = config.getPath()+"/"+pageAddr.replace(".","/")+"/";
		File filePath = new File(fileAddr);
		if(!filePath.exists()){
			filePath.mkdirs();
		}
		
		File file = new File(fileAddr,nameBean.getModelName()+".java");
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
		
		bw.write("package " + pageAddr+";");
		bw.newLine();
		bw.newLine();
		bw.newLine();
		bw.write("import javax.persistence.Id;");
		bw.newLine();
		bw.write("import javax.persistence.Table;");
		bw.newLine();
		bw.write("import java.io.Serializable;");
		bw.newLine();
		bw.write("import java.util.Date;");
		bw.newLine();
		ServiceCodeGeneratorUtil.outputAuthor(bw,nameBean.getClazzName()+" model 实体类");
		bw.newLine();
		
		bw.write("@Table(name=\""+nameBean.getTableName()+"\")");
		bw.newLine();
		bw.write("public class "+nameBean.getModelName()+" implements Serializable{");
		bw.newLine();
		
		
		
		for(TableDetailBean tableDetail : listBean){
			if(StringUtils.hasText(tableDetail.getComment())){
				bw.write("\t /**" + tableDetail.getComment() + "**/");
				bw.newLine();
			}
			if("id".equals(tableDetail.getFiled())){
				bw.write("\t@Id");
				bw.newLine();
			}
			bw.write("\tprivate " +  processType(tableDetail.getType()) + " " + tableDetail.getCodeField() + ";");
			bw.newLine();
		}
		
		generatorField(bw, listBean);
		
		generatorToString(bw, nameBean.getModelName(), listBean);
		
		bw.newLine();
		bw.write("}");
		bw.newLine();
		bw.flush();
		bw.close();
		
	}
	
	
	private static void generatorToString(BufferedWriter bw, String name, List<TableDetailBean> listBean)throws IOException {
		
		bw.newLine();
		bw.write("\t@Override");
		bw.newLine();
		bw.write("\tpublic String toString() {");
		bw.newLine();
		bw.newLine();
		bw.write("\t\t return \""+name+"{\" ");
		bw.newLine();
		for(TableDetailBean tableDetail : listBean){
			bw.write("\t\t\t + \" " +   tableDetail.getCodeField() + " = \" + this." +  tableDetail.getCodeField());
			bw.newLine();
		}
		bw.write("\t\t\t + \" }\";");
		bw.newLine();
		bw.write("\t}");
		bw.newLine();
		
	}
	
	
	private static void generatorField(BufferedWriter bw, List<TableDetailBean> listBean)  throws IOException{
		
		for(TableDetailBean tableDetail : listBean){
			String tempType = processType(tableDetail.getType());
			String tempField =  tableDetail.getCodeField();
			String upTempField = tableDetail.getUpWordField();
			bw.newLine();
			bw.write("\tpublic void set" + upTempField + "(" + tempType + " " + tempField + "){");
			bw.newLine();
			bw.write("\t\tthis." + tempField + " = " + tempField + ";");
			bw.newLine();
			bw.write("\t}");
			bw.newLine();
			
			bw.write("\tpublic " + tempType + " get" + upTempField + "(){");
			bw.newLine();
			bw.write("\t\treturn this."+tempField+";");
			bw.newLine();
			bw.write("\t}");
			bw.newLine();
		}
		
	}
	
	private static String processType(String type){
		if(type.indexOf(type_char)>-1){
			return "String";
		}else if(type.indexOf(type_int)>-1){
			
			if(type.indexOf(type_bigint)>-1){
				return "Long";
			}
			
			return "Integer";
		}else if(type.indexOf(type_date)>-1){
			return "Date";
		}else if(type.indexOf(type_text)>-1){
			return "String";
		}
		else if(type.indexOf(type_float)>-1){
			return "Float";
		}
		
		
		return null;
	}
 
}
