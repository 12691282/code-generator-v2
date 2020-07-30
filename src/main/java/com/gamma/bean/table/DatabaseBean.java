package com.gamma.bean.table;

import lombok.Data;

@Data
public class DatabaseBean {
	
	private String url;
	private String username; 
	private String password;
	private String driver;
	private String tableNameStr;
 
}
