package com.gamma.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import com.gamma.bean.table.DatabaseBean;

public class DataSourceHelper {
	
	public static final String MYSQL_DRIVER = "com.mysql.jdbc.Driver";  
	
	
	public Connection conn = null;  
	public PreparedStatement pst = null;  
	
	public static Connection connectToDatabase(DatabaseBean bean) throws Exception {

        	 Class.forName(MYSQL_DRIVER);//指定连接类型  
        	 return DriverManager.getConnection(bean.getUrl(), bean.getUsername(), bean.getPassword());
        	 
	}
		
}
