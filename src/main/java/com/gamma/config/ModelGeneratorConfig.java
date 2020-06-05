package com.gamma.config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.gamma.bean.table.DatabaseBean;
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
	  //bean 路径
	  private String  beanPackage;

	 //基础模块路径名称
	 private String  basePackageModelName;
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
	  //根路径
	  private String  rootPackage;

	//返回类全路径
	private String returnClassPath;

	//返回类名称
	private String returnClassName;

	private Boolean isBaseBean;
	  
	private Boolean isBaseModel;
	  
	private NameCollectionBean nameBean;
	  
	//压缩后的文件地址
	private String zipFileName;


	//准备配置信息数据
	public ModelGeneratorConfig(TableConfigBean bean) {

		//设置根路径
		this.setPath(bean.getPath());
		this.setDaoNameSuffix(bean.getDaoNameSuffix());
		this.setRootPackage(bean.getBasePackage() + "."+ bean.getBaseModelName());
		this.setBasePackageModelName(bean.getBasePackage() + "."+ bean.getBaseModelName());
		this.setMapperPackage(this.getBasePackageModelName() + "."+bean.getDaoNameSuffix());
		//实体类根路径
		this.setModelPackage(this.getBasePackageModelName()+".entity");
		this.setBeanPackage(this.getBasePackageModelName()+".bean");

		String[] classArray =  bean.getReturnClassPath().split("\\.");
		this.setReturnClassName(classArray[classArray.length - 1]);
		this.setReturnClassPath(bean.getReturnClassPath());

		this.mkdir();
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
