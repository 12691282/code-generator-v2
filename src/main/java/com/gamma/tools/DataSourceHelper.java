package com.gamma.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Properties;

import com.gamma.bean.table.DatabaseBean;

public class DataSourceHelper {
	
	public static final String MYSQL_DRIVER = "com.mysql.jdbc.Driver";  

	public static Connection connectToDatabase(DatabaseBean bean) throws Exception {

		Class.forName(MYSQL_DRIVER);//指定连接类型
		Properties props =new Properties();
		props.setProperty("user", bean.getUsername());
		props.setProperty("password",  bean.getPassword());
		props.setProperty("remarks", "true"); //设置可以获取remarks信息
		props.setProperty("useInformationSchema", "true");//设置可以获取tables remarks信息
        return DriverManager.getConnection(bean.getUrl(), props);
        	 
	}
		
}
