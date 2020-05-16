package com.gamma.bean.table;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class TableConfigBean {
	//基础路径
	private String  basePackage;
	//基础模块名称
	private String  baseModelName;
	//数据层后缀名称(Dao 或 Mapper 或其他)
	private String daoNameSuffix;
	//页面样式
	private String  pageCss;
	//返回类全路径
	private String returnClassPath;

	private String path;

	private Boolean isBaseBean;

	private Boolean isBaseModel;

	private String[] tableArr;

}
