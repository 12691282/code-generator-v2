package com.gamma;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@EnableAutoConfiguration
public class CodeGenerateApplication {
	
	private static Logger logger = Logger.getLogger(CodeGenerateApplication.class);
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(CodeGenerateApplication.class, args); 
		logger.info("service start success !");
	}

}
