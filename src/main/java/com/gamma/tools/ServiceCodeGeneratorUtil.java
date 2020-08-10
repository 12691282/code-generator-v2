package com.gamma.tools;

import java.util.Arrays;

/**
 * 服务端代码生成帮助器
 * @author Administrator
 *
 */
public class ServiceCodeGeneratorUtil {
	

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


}
