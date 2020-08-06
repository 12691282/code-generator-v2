package com.gamma.bean;

import lombok.Data;

@Data
public class DatabaseBean {
	
	private String url;
	private String username; 
	private String password;
	private String driver;
	private String tableNameStr;
 
}
