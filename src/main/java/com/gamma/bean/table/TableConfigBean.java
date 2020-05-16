package com.gamma.bean.table;

import lombok.Data;

import java.util.Arrays;

@Data
public class TableConfigBean {

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

	private String[] tableArr;
	  
	@Override
	public String toString() {
		return "TableConfigBean [beanPackage=" + beanPackage + ", mapperPackage=" + mapperPackage + ", modelPackage="
				+ modelPackage + ", xmlPackage=" + xmlPackage + ", pageCss=" + pageCss + ", tableArr="
				+ Arrays.toString(tableArr) + "]";
	}
}
