package com.gamma.generator.mybatis;


import com.gamma.bean.code.NameCollectionBean;
import com.gamma.config.ModelGeneratorConfig;
import com.gamma.tools.CommonForPageConfig;
import com.gamma.tools.FileTools;
import com.gamma.tools.ServiceCodeGeneratorUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;


/**
 * controller 生成
 * @throws IOException
 */
public class GeneratorToControllerHelper {
	
	
	public static void outputController( ModelGeneratorConfig config) throws IOException {
		NameCollectionBean nameBean = config.getNameBean();
		String serviceObj = nameBean.getLowerHeadWordClassName()+"Service";
		String codePageAddr = config.getBasePackageModelName() + ".controller";
		String fileAddr = config.getPath()+"/"+codePageAddr.replace(".","/")+"/";
		
		File filePath = new File(fileAddr);
		if(!filePath.exists()){
			filePath.mkdirs();
		}
		
		BufferedWriter bw = FileTools.writerFiterByUTF8(fileAddr,nameBean.getControllerName());
		
		bw.write("package " + codePageAddr+";");
		bw.newLine();
		bw.newLine();
		
		bw.newLine();
		bw.newLine();
		bw.write("import "+config.getBeanPackage()+"."+nameBean.getBeanName()+";");
		bw.newLine();
		bw.write("import "+config.getBasePackageModelName()+".service."+nameBean.getServiceName()+";");
		bw.newLine();
		bw.write("import com.petrochina.bd.common.utils.PageUtils;");
		bw.newLine();
		bw.write("import org.springframework.beans.factory.annotation.Autowired;");
		bw.newLine();
		bw.write("import org.springframework.web.bind.annotation.RequestMapping;");
		bw.newLine();
		bw.write("import org.springframework.web.bind.annotation.RequestBody;");
		bw.newLine();
		bw.write("import org.springframework.web.bind.annotation.RestController;");
		bw.newLine();
		bw.write("import lombok.extern.slf4j.Slf4j;");
		bw.newLine();
		bw.write("import com.petrochina.bd.common.utils.CnpcHttpResponse;");
		bw.newLine();
		ServiceCodeGeneratorUtil.outputAuthorByName(bw,nameBean.getClazzName()+" Controller");
		bw.newLine();
		bw.write("@RestController");
		bw.newLine();
		bw.write("@RequestMapping(\""+nameBean.getLowerHeadWordClassName()+"\")");
		bw.newLine();
		bw.write("@Slf4j");
		bw.newLine();
		bw.write("public class "+nameBean.getControllerName()+" extends BaseController{");
		bw.newLine();
		bw.write("\t/**service服务类**/");
		bw.newLine();
		bw.write("\t@Autowired");
		bw.newLine();
		bw.write("\tprivate "+nameBean.getServiceName() + " " + serviceObj+";");
		bw.newLine();
		bw.newLine();
		bw.newLine();
		bw.write("\t/**列表**/");
		bw.newLine();
		bw.write("\t@RequestMapping(\""+CommonForPageConfig.LIST_METHOD_URL+"\")");
		bw.newLine();
		bw.write("\tpublic CnpcHttpResponse get"+nameBean.getClazzName()+"List(@RequestBody "+nameBean.getBeanName()+" bean){");
		bw.newLine();
		bw.write("\t\t log.info(\""+nameBean.getBeanName()+": {}\",bean);");
		bw.newLine();
		bw.write("\t\t //开始分页");
		bw.newLine();
		bw.write("\t\tPageUtils page = " + serviceObj+"."+nameBean.getListMethodName()+"(bean);");
		bw.newLine();
		bw.write("\t\t return CnpcHttpResponse.ok().put(\"page\", page);");
		bw.newLine();
		bw.write("\t}");
		bw.newLine();
		bw.newLine();
		bw.write("\t/**根据id获取对象**/");
		bw.newLine();
		bw.write("\t@RequestMapping(\""+CommonForPageConfig.GET_BEANMETHOD_URL+"\")");
		bw.newLine();
		bw.write("\tpublic CnpcHttpResponse get"+nameBean.getBeanName()+"ById(@RequestBody "+nameBean.getBeanName()+" bean){");
		bw.newLine();
		bw.write("\t\tlog.info(\""+nameBean.getBeanName()+": {}\",bean);");
		bw.newLine();
		bw.write("\t\tif(bean.getId() != null){");
		bw.newLine();
		bw.write("\t\t\tbean = "+serviceObj+"."+nameBean.getGetBeanMethodName()+"(bean);");
		bw.newLine();
		bw.write("\t\t}");
		bw.newLine();
		bw.write("\t\t"+"return  CnpcHttpResponse.result(bean);");
		bw.newLine();
		bw.write("\t}");
		
		bw.newLine();
		bw.newLine();
		bw.write("\t/***新增 或修改**/");
		bw.newLine();
		bw.write("\t@RequestMapping(\""+CommonForPageConfig.ADD_OR_MODIFY_METHOD_URL+"\")");
		bw.newLine();
		bw.write("\tpublic CnpcHttpResponse add"+nameBean.getBeanName()+"(@RequestBody "+nameBean.getBeanName()+" bean){");
		bw.newLine();
		bw.write("\t\tlog.info(\""+nameBean.getBeanName()+": {}\",bean);");
		bw.newLine();
		bw.write("\t\ttry{");
		bw.newLine();
		bw.newLine();
		bw.write("\t\t\t"+serviceObj+"."+nameBean.getAddOrModifyMethodName()+"(bean);");
		bw.newLine();
		bw.newLine();
		bw.write("\t\t}catch(Exception e){");
		bw.newLine();
		bw.write("\t\t\tlog.error(e.getMessage());");
		bw.newLine();
		bw.write("\t\t\treturn CnpcHttpResponse.error();");
		bw.newLine();
		bw.write("\t\t}");
		bw.newLine();
		bw.write("\t\treturn CnpcHttpResponse.ok();");
		bw.newLine();
		bw.write("\t}");
		
		bw.newLine();
		bw.newLine();
		bw.write("\t/**删除**/");
		bw.newLine();
		bw.write("\t@RequestMapping(\""+CommonForPageConfig.DELETE_METHOD_URL+"\")");
		bw.newLine();
		bw.write("\tpublic CnpcHttpResponse delete"+nameBean.getBeanName()+"(@RequestBody "+nameBean.getBeanName()+" bean){");
		bw.newLine();
		bw.write("\t\tlog.info(\""+nameBean.getBeanName()+": {}\",bean);");
		bw.newLine();
		bw.write("\t\ttry{");
		bw.newLine();
		bw.newLine();
		bw.write("\t\t\t"+serviceObj+"."+nameBean.getDeleteMethodName()+"(bean);");
		bw.newLine();
		bw.newLine();
		bw.write("\t\t}catch(Exception e){");
		bw.newLine();
		bw.write("\t\t\tlog.error(e.getMessage());");
		bw.newLine();
		bw.write("\t\t\treturn CnpcHttpResponse.error();");
		bw.newLine();
		bw.write("\t\t}");
		bw.newLine();
		bw.write("\t\treturn CnpcHttpResponse.ok();");
		bw.newLine();
		bw.write("\t}");
		bw.newLine();
		bw.newLine();
		bw.write("}");
		bw.newLine();
		bw.flush();
		bw.close();
		
	}

}
