package com.gamma.config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.Data;
import org.joda.time.DateTime;
import org.springframework.core.io.Resource;

import com.gamma.bean.code.NameCollectionBean;
import com.gamma.bean.table.TableConfigBean;
import com.gamma.bean.table.TableDetailBean;

@Data
public class ModelGeneratorConfig {
		
	  // 1: 整型，2:字符，3:(Long)长整型 4:double型
	  private Integer idType;
	  //模块名称
	  private String  modelName;
	  //bean 路径
	  private String  beanPackage;
	  //dao路径 mapper
	  private String  mapperPackage;
	  //数据层后缀名称(Dao 或 Mapper 或其他)
	  private String daoNameSuffix;
	  //原型model package 配置路径
	  private String  modelPackage;
	  //mybatis xml 配置路径
	  private String  xmlPackage;
	  
	  private String  pageCss;
	  
	  private String path;
	  //基础路径
	  private String  basePackage;
	  
	  private Boolean isBaseBean;
	  
	  private Boolean isBaseModel;
	  
	  private NameCollectionBean nameBean;
	  
	  //压缩后的文件地址
	  private String zipFileName;
	  
	public ModelGeneratorConfig(TableConfigBean bean){
		this.setBasePackage(bean.getBasePackage());
		this.setBeanPackage(bean.getBeanPackage());
		this.setMapperPackage(bean.getMapperPackage());
		this.setModelPackage(bean.getModelPackage());
		this.setPath(bean.getPath());
		this.setXmlPackage(bean.getXmlPackage());
		this.setPageCss(bean.getPageCss());
		this.setIsBaseBean(bean.getIsBaseBean());
		this.setIsBaseModel(bean.getIsBaseModel());
	}

	public void mkdir() {
		String filePathName = new DateTime().toString("yyyymmddHHmmss");
		String filePath = this.getPath()+"\\"+filePathName;
		
		File folder = new File(filePath);
		if(!folder.exists()){
			folder.mkdir();
		}
		
		this.setZipFileName(this.getPath()+"\\"+filePathName+".zip");
		this.setPath(filePath);
		
	}
	
}
