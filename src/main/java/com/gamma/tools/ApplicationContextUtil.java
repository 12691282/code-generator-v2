package com.gamma.tools;


import org.springframework.context.ApplicationContext;

public class ApplicationContextUtil {
	
    private static ApplicationContext applicationContext;

    public static void setApplicationContext(ApplicationContext context) {
	   
       	applicationContext = context;
       	
    }
  
   
   
    public static ApplicationContext getApplicationContext() {
	   
	   return applicationContext;
	   
    }

}
