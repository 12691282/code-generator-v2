package com.gamma.generator.mybatis;


import java.io.BufferedWriter;
import java.io.IOException;

import com.gamma.tools.CommonForPageConfig;


/**
 * controller 生成
 * @param config
 * @throws IOException
 */
public class GeneratorToHtmlHelper {
	
	
	public static void toGeneratorHeadPart( BufferedWriter bw) throws IOException {
		bw.write("<%@ page language=\"java\" contentType=\"text/html; charset=UTF-8\" pageEncoding=\"UTF-8\"%>");
		bw.newLine();
		bw.write(CommonForPageConfig.BASE_JSP_INCLUD_FONFIG);
		bw.newLine();
	}

}
