package com.gamma.bean.code;

import java.util.List;

import com.gamma.bean.table.TableDetailBean;
import com.gamma.tools.ServiceCodeGeneratorUtil;

public class PageInfoCollectionBean {
	
	  private String clazzName;
	
	  private String lowerHeadWordClassName;
	
	  private String controllerName;
	  
	  private String serviceName;
	  
	  private String mapperName;
	  
	  private String modelName;
	  
	  private String beanName;
	  
	  private String xmlName;
	  
	  
	  /**
	   * 方法名称
	   */
	  private String listMethodName;
	  
	  private String addMethodName;
	  
	  private String modifytMethodName;
	  
	  private String deleteMethodName;
	  
	  /**
	   * 表名
	   * @param tableName
	   */
	  private String tableName;
	  
	  private  List<TableDetailBean> listBean;
	  
		
		public PageInfoCollectionBean(String tableName) {
			String clazzName =  ServiceCodeGeneratorUtil.changeTableName(tableName);
			String lowerHeadWord = ServiceCodeGeneratorUtil.toHeadWordLowerCase(clazzName);
			
			this.setTableName(tableName);
			
			this.setLowerHeadWordClassName(lowerHeadWord);
			this.setClazzName(clazzName);
			this.setBeanName(clazzName+"Bean");
			this.setControllerName(clazzName+"Controller");
			this.setServiceName(clazzName+"Service");
			this.setMapperName(clazzName+"Mapper");
			this.setModelName( clazzName+"Model");
			this.setXmlName(clazzName+"Xml");
			
			this.setListMethodName("get"+clazzName+"ListByBean");
			this.setAddMethodName("add"+clazzName+"ByBean");
			this.setModifytMethodName("modify"+clazzName+"ByBean");
			this.setDeleteMethodName("delete"+clazzName+"ByBean");
		}


		public String getControllerName() {
			return controllerName;
		}



		public void setControllerName(String controllerName) {
			this.controllerName = controllerName;
		}



		public String getServiceName() {
			return serviceName;
		}



		public void setServiceName(String serviceName) {
			this.serviceName = serviceName;
		}



		public String getMapperName() {
			return mapperName;
		}



		public void setMapperName(String mapperName) {
			this.mapperName = mapperName;
		}



		public String getModelName() {
			return modelName;
		}



		public void setModelName(String modelName) {
			this.modelName = modelName;
		}



		public String getBeanName() {
			return beanName;
		}



		public void setBeanName(String beanName) {
			this.beanName = beanName;
		}



		public String getXmlName() {
			return xmlName;
		}



		public void setXmlName(String xmlName) {
			this.xmlName = xmlName;
		}




		public String getClazzName() {
			return clazzName;
		}


		public void setClazzName(String clazzName) {
			this.clazzName = clazzName;
		}


		public String getLowerHeadWordClassName() {
			return lowerHeadWordClassName;
		}


		public void setLowerHeadWordClassName(String lowerHeadWordClassName) {
			this.lowerHeadWordClassName = lowerHeadWordClassName;
		}


		public String getListMethodName() {
			return listMethodName;
		}


		public void setListMethodName(String listMethodName) {
			this.listMethodName = listMethodName;
		}


		public String getAddMethodName() {
			return addMethodName;
		}


		public void setAddMethodName(String addMethodName) {
			this.addMethodName = addMethodName;
		}


		public String getModifytMethodName() {
			return modifytMethodName;
		}


		public void setModifytMethodName(String modifytMethodName) {
			this.modifytMethodName = modifytMethodName;
		}


		public String getDeleteMethodName() {
			return deleteMethodName;
		}


		public void setDeleteMethodName(String deleteMethodName) {
			this.deleteMethodName = deleteMethodName;
		}


		public String getTableName() {
			return tableName;
		}


		public void setTableName(String tableName) {
			this.tableName = tableName;
		}


		public List<TableDetailBean> getListBean() {
			return listBean;
		}


		public void setListBean(List<TableDetailBean> listBean) {
			this.listBean = listBean;
		}

}
