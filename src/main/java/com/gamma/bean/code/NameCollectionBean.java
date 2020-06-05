package com.gamma.bean.code;

import java.util.List;

import com.gamma.bean.table.TableDetailBean;
import com.gamma.tools.ServiceCodeGeneratorUtil;
import lombok.Data;

@Data
public class NameCollectionBean {
	
	  private String clazzName;
	
	  private String lowerHeadWordClassName;
	
	  private String controllerName;
	  
	  private String serviceName;
	  
	  private String mapperName;
	  
	  private String modelName;
	  
	  private String beanName;
	  
	  private String xmlName;
	  
	  
	  /*******************************************************************
	   * 方法名称
	   */
	  private String listMethodName;
	  
	  private String getBeanMethodName;
	  
	  private String addOrModifyMethodName;
	  
	  private String deleteMethodName;
	  /**
	   * bean 准备化数据名称
	   */
	  private String beanPrepareDataMethodName = "prepareData";
	  /**
	   * mapper 方法名 TODO
	   */
	  private String mapperFindByModelMethodName = "findHouseInfoListByBean";
	  
	  /*******************************************************************
	   * 表名
	   * @param tableName
	   */
	  private String tableName;
	  
	  private  List<TableDetailBean> listBean;
	  
		
		public NameCollectionBean(String tableName, String daoSuffix) {
			String clazzName =  ServiceCodeGeneratorUtil.changeTableName(tableName);
			String lowerHeadWord = ServiceCodeGeneratorUtil.toHeadWordLowerCase(clazzName);
			
			this.setTableName(tableName);
			
			this.setLowerHeadWordClassName(lowerHeadWord);
			this.setClazzName(clazzName);
			this.setBeanName(clazzName+"Bean");
			this.setControllerName(clazzName+"Controller");
			this.setServiceName(clazzName+"Service");
			this.setMapperName(clazzName+ServiceCodeGeneratorUtil.toHeadWordUpperCase(daoSuffix));
			this.setModelName( clazzName+"Entity");
			this.setXmlName(clazzName+"Xml");
			
			this.setListMethodName("get"+clazzName+"ListByBean");
			this.setGetBeanMethodName("get"+clazzName+"BeanById");
			this.setAddOrModifyMethodName("addOrModify"+clazzName+"ByBean");
			this.setDeleteMethodName("delete"+clazzName+"ByBean");

			//设置mybatis 查询id
			this.setMapperFindByModelMethodName("get"+clazzName+"List");
		}




		public String getBeanPrepareDataMethodName() {
			return beanPrepareDataMethodName;
		}


		public void setBeanPrepareDataMethodName(String beanPrepareDataMethodName) {
			this.beanPrepareDataMethodName = beanPrepareDataMethodName;
		}


		public String getMapperFindByBeanMethodName() {
			return mapperFindByModelMethodName;
		}


		public void setMapperFindByModelMethodName(String mapperFindByModelMethodName) {
			this.mapperFindByModelMethodName = mapperFindByModelMethodName;
		}

}
