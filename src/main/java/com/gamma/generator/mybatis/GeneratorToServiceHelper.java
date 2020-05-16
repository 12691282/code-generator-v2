package com.gamma.generator.mybatis;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.springframework.util.StringUtils;

import com.gamma.bean.code.NameCollectionBean;
import com.gamma.bean.table.TableDetailBean;
import com.gamma.config.ModelGeneratorConfig;
import com.gamma.tools.FileTools;
import com.gamma.tools.ServiceCodeGeneratorUtil;

/**
 * service 生成助手
 * @author Administrator
 *
 */
public class GeneratorToServiceHelper {
	
	public static void outputService(ModelGeneratorConfig config)throws IOException {
		//接口生成
		toGeneratorServiceInterface(config);
		
	   //实现类生成
		toGeneratorServiceInterfaceImplements(config);
	}
	
	private static void toGeneratorServiceInterface(ModelGeneratorConfig config)throws IOException  {
		NameCollectionBean nameBean = config.getNameBean();
		String pageAddr = config.getBasePackage() + ".service."+nameBean.getLowerHeadWordClassName();
		String fileAddr = config.getPath()+"/"+pageAddr.replace(".","/")+"/";
		File filePath = new File(fileAddr);
		if(!filePath.exists()){
			filePath.mkdirs();
		}
		
		BufferedWriter bw = FileTools.writerFiterByUTF8(fileAddr,nameBean.getServiceName());
		
		bw.write("package " + pageAddr+";");
		bw.newLine();
		bw.newLine();
		bw.write("import "+config.getBeanPackage()+"."+nameBean.getLowerHeadWordClassName()+"."+nameBean.getBeanName()+";");
		bw.newLine();
		bw.newLine();
		bw.write("import com.github.pagehelper.PageInfo;");
		bw.newLine();
		ServiceCodeGeneratorUtil.outputAuthor(bw,nameBean.getClazzName()+" service 接口");
		bw.newLine();
		bw.write("public interface "+nameBean.getServiceName()+"{");
		bw.newLine();
		bw.write("\t /**列表**/");
		bw.newLine();
		bw.write("\t PageInfo "+nameBean.getListMethodName()+"("+nameBean.getBeanName()+" bean);");
		
		bw.newLine();
		bw.write("\t /**根据id获取bean对象**/");
		bw.newLine();
		bw.write("\t "+nameBean.getBeanName()+" "+nameBean.getGetBeanMethodName()+"("+nameBean.getBeanName()+" bean);");
		
		bw.newLine();
		bw.write("\t /**新增 或 修改**/");
		bw.newLine();
		bw.write("\t void "+nameBean.getAddOrModifyMethodName()+"("+nameBean.getBeanName()+" bean);");
		
		bw.newLine();
		bw.write("\t /**删除**/");
		bw.newLine();
		bw.write("\t void "+nameBean.getDeleteMethodName()+"("+nameBean.getBeanName()+" bean);");
		
		bw.newLine();
		bw.write("}");
		bw.newLine();
		bw.flush();
		bw.close();
	}
	
	private static void toGeneratorServiceInterfaceImplements(ModelGeneratorConfig config) throws IOException {
		NameCollectionBean nameBean = config.getNameBean();
		String pageAddr = config.getBasePackage() + ".service."+nameBean.getLowerHeadWordClassName()+".impl";
		String fileAddr = config.getPath()+"/"+pageAddr.replace(".","/")+"/";
		File filePath = new File(fileAddr);
		if(!filePath.exists()){
			filePath.mkdirs();
		}
		
		File serviceImplFile = new File(fileAddr,nameBean.getServiceName()+"Impl.java");
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(serviceImplFile)));
		
		String mapperObj = nameBean.getLowerHeadWordClassName() + "Mapper";
		
		bw.write("package " + pageAddr+";");
		bw.newLine();
		bw.newLine();
		bw.write("import "+config.getBeanPackage()+"."+nameBean.getLowerHeadWordClassName()+"."+nameBean.getBeanName()+";");
		bw.newLine();
		bw.write("import "+config.getModelPackage()+"."+nameBean.getLowerHeadWordClassName()+"."+nameBean.getModelName()+";");
		bw.newLine();
		bw.write("import "+config.getBasePackage()+".service."+nameBean.getLowerHeadWordClassName()+"."+nameBean.getServiceName()+";");
		bw.newLine();
		bw.write("import "+config.getMapperPackage ()+"."+nameBean.getMapperName()+";");
		bw.newLine();
		bw.write("import "+config.getBasePackage()+".base.BaseService;");
		bw.newLine();
		bw.write("import "+config.getBasePackage()+".tools.DateUtil;");
		bw.newLine();
		bw.write("import "+config.getBasePackage()+".tools.IDGeneratorUtil;");
		bw.newLine();
		bw.write("import com.github.pagehelper.Page;");
		bw.newLine();
		bw.write("import com.github.pagehelper.PageInfo;");
		bw.newLine();
		bw.newLine();
		bw.write("import org.springframework.beans.factory.annotation.Autowired;");
		bw.newLine();
		bw.write("import org.springframework.stereotype.Service;");
		bw.newLine();
		bw.write("import org.springframework.util.StringUtils;");
		bw.newLine();
		bw.write("import org.springframework.beans.BeanUtils;");
		bw.newLine();
		bw.write("import org.apache.log4j.Logger;");
		bw.newLine();
		ServiceCodeGeneratorUtil.outputAuthor(bw,nameBean.getClazzName()+" service 实现类");
		bw.write("@Service");
		bw.newLine();
		bw.write("public class "+nameBean.getServiceName()+"Impl extends BaseService implements "+nameBean.getServiceName()+"{");
		bw.newLine();
		bw.newLine();
		bw.write("\tprivate static Logger logger = Logger.getLogger("+nameBean.getServiceName()+"Impl.class);");
		bw.newLine();
		bw.newLine();
		bw.write("\t@Autowired");
		bw.newLine();
		bw.write("\tprivate "+nameBean.getMapperName() + " " + mapperObj+";");
		bw.newLine();
		bw.newLine();
		bw.write("\t/**列表**/");
		bw.newLine();
		bw.write("\t@Override");
		bw.newLine();
		bw.write("\tpublic PageInfo "+nameBean.getListMethodName()+"("+nameBean.getBeanName()+" bean){");
		ServiceCodeGeneratorUtil.outputLogger(bw, "\""+nameBean.getListMethodName()+" bean : \" +  bean.toString()");
		bw.newLine();
		bw.write("\t\t"+nameBean.getModelName() + " model = new "+ nameBean.getModelName()+"();");
		bw.newLine();
		bw.newLine();
		bw.write("\t\tPage pageInfo = super.setPageNumber();");
		bw.newLine();
		bw.write("\t\t"+mapperObj+"."+nameBean.getMapperFindByBeanMethodName()+"(bean);");
		bw.newLine();
		bw.write("\t\treturn pageInfo.toPageInfo();");
		bw.newLine();
		bw.write("\t}");
		
		bw.newLine();
		bw.newLine();
		bw.write("\t /**根据id获取bean对象**/");
		bw.newLine();
		bw.write("\t@Override");
		bw.newLine();
		bw.write("\tpublic "+nameBean.getBeanName()+" "+nameBean.getGetBeanMethodName()+"("+nameBean.getBeanName()+" bean){");
		bw.newLine();
		bw.write("\t\t"+nameBean.getModelName() + " model = " +mapperObj+".selectByPrimaryKey(bean.getId());");
		bw.newLine();
		bw.write("\t\t"+nameBean.getBeanName() + " newBean = new "+ nameBean.getBeanName()+"();");
		bw.newLine();
		bw.write("\t\tBeanUtils.copyProperties(model, newBean);");
		bw.newLine();
		
		for(TableDetailBean bean : nameBean.getListBean()){
			//判断时间域
			if(bean.isDateTypeField()){
				String upTempField = bean.getUpWordField();
				
				bw.write("\t\tif(!StringUtils.isEmpty(model.get"+upTempField+"())){");
				bw.newLine();
				bw.write("\t\t\tnewBean.set"+upTempField+"(DateUtil.toStringForThree(model.get"+upTempField+"()));");
				bw.newLine();
				bw.write("\t\t}");
				bw.newLine();
				
			}
			
		}
		
		bw.write("\t\t return newBean;");
		bw.newLine();
		bw.write("\t}");
		
		bw.newLine();
		bw.newLine();
		bw.write("\t/**新增 或修改**/");
		bw.newLine();
		bw.write("\t@Override");
		bw.newLine();
		bw.write("\tpublic void "+nameBean.getAddOrModifyMethodName()+"("+nameBean.getBeanName()+" bean){");
		ServiceCodeGeneratorUtil.outputLogger(bw, "\""+nameBean.getAddOrModifyMethodName()+" bean  : \"   +  bean.toString()");
		bw.newLine();
		bw.write("\t\t"+nameBean.getModelName() + " model = new "+ nameBean.getModelName()+"();");
		bw.newLine();
		bw.write("\t\tBeanUtils.copyProperties(bean, model);");
		bw.newLine();
		bw.write("\t\t String uuid = null;");
		bw.newLine();
		
		for(TableDetailBean bean : nameBean.getListBean()){
			//判断时间域
			if(bean.isDateTypeField()){
				String upTempField = bean.getUpWordField();
				
				bw.write("\t\tif(!StringUtils.isEmpty(bean.get"+upTempField+"())){");
				bw.newLine();
				bw.write("\t\t\tmodel.set"+upTempField+"(DateUtil.toDate(bean.get"+upTempField+"()));");
				bw.newLine();
				bw.write("\t\t}");
				bw.newLine();
				continue;
				
			}
			if("id".equals(bean.getFiled()) && bean.isVarcharTypeField()){
				if(!StringUtils.isEmpty(bean.getLength())){
					Integer length = Integer.valueOf(bean.getLength());
					if(length > 32){
						bw.write("\t\t uuid = IDGeneratorUtil.getUUID();");
					}else{
						bw.write("\t\t uuid = IDGeneratorUtil.getUUID().substring(0, "+(length-1)+");");
					}
					bw.newLine();
				}
			}
		}
		
		bw.write("\t\tif(model.getId() != null){");
		bw.newLine();
		bw.write("\t\t\t"+mapperObj+".updateByPrimaryKey(model);");
		bw.newLine();
		bw.write("\t\t}else{");
		bw.newLine();
		bw.write("\t\t\tif(uuid != null){");
		bw.newLine();
		bw.write("\t\t\t\tmodel.setId(uuid);");
		bw.newLine();
		bw.write("\t\t\t}");
		bw.newLine();
		bw.write("\t\t\t"+mapperObj+".insert(model);");
		bw.newLine();
		bw.write("\t\t}");
		bw.newLine();
		bw.write("\t}");
		
		bw.newLine();
		bw.newLine();
		bw.write("\t/**删除**/");
		bw.newLine();
		bw.write("\t@Override");
		bw.newLine();
		bw.write("\tpublic void "+nameBean.getDeleteMethodName()+"("+nameBean.getBeanName()+" bean){");
		ServiceCodeGeneratorUtil.outputLogger(bw, "\""+ nameBean.getDeleteMethodName()+" bean : \"  + bean.toString()");
		bw.newLine();
		bw.write("\t\t"+nameBean.getModelName() + " model = new "+ nameBean.getModelName()+"();");
		bw.newLine();
		bw.write("\t\tBeanUtils.copyProperties(bean, model);");
		bw.newLine();
		bw.write("\t\tif(model.getId() != null){");
		bw.newLine();
		bw.write("\t\t\t"+mapperObj+".deleteByPrimaryKey(model);");
		bw.newLine();
		bw.write("\t\t}");
		bw.newLine();
		bw.write("\t}");
		
		bw.newLine();
		bw.write("}");
		bw.newLine();
		bw.flush();
		bw.close();
		
	}
}
