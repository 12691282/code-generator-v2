package com.gamma.generator.mybatis;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
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
		String pageAddr = config.getBeanPackage();
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
		bw.write("import lombok.Data;");
		bw.newLine();
		bw.write("import lombok.ToString;");
		bw.newLine();
		bw.write("import java.io.Serializable;");
		bw.newLine();
		bw.newLine();
		ServiceCodeGeneratorUtil.outputAuthorByName(bw,config.getTableRemarks()+" bean类");
		bw.newLine();
		bw.write("@Data");
		bw.newLine();
		bw.write("@ToString");
		bw.newLine();
		bw.write("public class "+nameBean.getBeanName()+" implements Serializable{");
		bw.newLine();
		
		for(TableDetailBean tableDetail : listBean){
			
			if(StringUtils.hasText(tableDetail.getComment())){
				bw.write("\t /**" + tableDetail.getComment() + "**/");
				bw.newLine();
			}
			bw.write("\tprivate String " + tableDetail.getCodeField() + ";");
			bw.newLine();
			
		}

		bw.write("}");
		bw.newLine();
		bw.flush();
		bw.close();
	}
	
	private static void toGeneratorModelEntityClazz(ModelGeneratorConfig config) throws IOException {
		NameCollectionBean nameBean = config.getNameBean();
		List<TableDetailBean> listBean = nameBean.getListBean();
		String pageAddr = config.getModelPackage();
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
		bw.newLine();
		bw.write("import com.baomidou.mybatisplus.annotation.TableField;");
		bw.newLine();
		bw.write("import com.baomidou.mybatisplus.annotation.TableName;");
		bw.newLine();
		bw.write("import lombok.Data;");
		bw.newLine();
		bw.write("import lombok.ToString;");
		bw.newLine();
		bw.write("import java.io.Serializable;");
		bw.newLine();
		bw.newLine();
		if(config.getIsBaseModel()){
			bw.write("import " + config.getBaseModelPath());
			bw.newLine();
		}

		ServiceCodeGeneratorUtil.outputAuthorByName(bw,nameBean.getClazzName()+" model 实体类");
		bw.newLine();
		bw.write("@Data");
		bw.newLine();
		bw.write("@ToString");
		bw.newLine();
		bw.write("@TableName(\""+nameBean.getTableName()+"\")");
		bw.newLine();
		String modelHeadString  = "public class "+nameBean.getModelName();

		if(config.getIsBaseModel()){
			modelHeadString += " extends " + config.getBaseModelName();
		}
		bw.write( modelHeadString + " implements Serializable{");
		bw.newLine();

		for(TableDetailBean tableDetail : listBean){

			boolean isPassed = false;
			//如果基础基础类侧过滤基础类字段
			if(config.getIsBaseModel()){
				for(String baseFiled : config.getBaseFiledList()){
					if(baseFiled.equalsIgnoreCase(tableDetail.getFiled())){
						isPassed = true;
						break;
					}
				}
			}

			if(isPassed){
				continue;
			}
			if(StringUtils.hasText(tableDetail.getComment())){
				bw.write("\t /**" + tableDetail.getComment() + "**/");
				bw.newLine();
			}
			bw.write("\t@TableField(\""+tableDetail.getFiled()+"\")");
			bw.newLine();
			bw.write("\tprivate " +  processType(tableDetail.getType()) + " " + tableDetail.getCodeField() + ";");
			bw.newLine();
		}

		bw.newLine();
		bw.write("}");
		bw.newLine();
		bw.flush();
		bw.close();
		
	}

	//base 字段过滤
//	private static boolean checkBaseParams(String filed) {
//		for(String item : baseFiledList){
//			if(item.equalsIgnoreCase(filed)){
//				return true;
//			}
//		}
//		return false;
//	}


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
