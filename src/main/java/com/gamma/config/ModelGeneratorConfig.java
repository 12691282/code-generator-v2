package com.gamma.config;

import com.gamma.bean.code.NameCollectionBean;
import com.gamma.bean.table.TableConfigBean;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import java.io.File;
import java.util.List;

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

	  private Boolean isBaseModel;

	  //baseModel 路径
	  private String baseModelPath;

	  //baseModel 名称
	  private String baseModelName;

	  private NameCollectionBean nameBean;

	  //压缩后的文件地址
	  private String zipFileName;

	  //基础类字段
	  private List<String> baseFiledList;

	  //表注释
	  private String tableRemarks;

	//准备配置信息数据
	public ModelGeneratorConfig(TableConfigBean bean) {
		//设置baseModel
		if("true".equals(bean.getIsBaseModel())){
			this.setIsBaseModel(Boolean.TRUE);
			this.setBaseModelPath(bean.getBaseModelPath());
			if(StringUtils.isNotEmpty(bean.getBaseModelPath())){
				String[] baseModelArr = bean.getBaseModelPath().split("\\.");
				this.baseModelName = baseModelArr[baseModelArr.length - 1];
			}
		}else {
			this.setIsBaseModel(Boolean.FALSE);
		}

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

		this.setPageCss(bean.getPageCss());

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
