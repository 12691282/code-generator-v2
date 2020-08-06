package com.gamma.bean;

import lombok.Data;


@Data
public class TableDetailBean {
	//字段名
	private String filed;
	//字段类型 : int, double,varchar
	private String type;
	//注释
	private String comment;
	private String def;
	//是否为空
	private String isNull;
	//字段长度
	private String length;
	
	//驼峰标识的字段
	private String codeField;
	
	//转化表单字段成代码驼峰标识字段样式
	public void processField() {
		
		if(this.filed == null || this.filed.length() == 0){
			return;
		}
		StringBuffer sb = new StringBuffer(this.filed.length());
		String field = this.filed.toLowerCase();
		String[] fields = field.split("_");
		String temp = null;
		sb.append(fields[0]);
		for(int i=1;i<fields.length;i++){
			temp = fields[i].trim();
			sb.append(temp.substring(0,1).toUpperCase())
			.append(temp.substring(1));
		}
		codeField =  sb.toString();
	}
	
	public boolean isDateTypeField() {
		return this.getType().indexOf("date")>-1;
	}
	public boolean isVarcharTypeField() {
		return this.getType().indexOf("char")>-1;
	}
	
	public String getCodeFieldForStartTime() {
		return codeField+"Start";
	}
	
	public String getCodeFieldForEndTime() {
		return codeField+"End";
	}
	
	
	public String getUpWordField() {
		return this.codeField.substring(0,1).toUpperCase()+this.codeField.substring(1);
	}

	



}
