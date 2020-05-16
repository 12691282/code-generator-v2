package com.gamma.service.table.impl;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.gamma.base.BaseService;
import com.gamma.bean.code.NameCollectionBean;
import com.gamma.bean.table.DatabaseBean;
import com.gamma.bean.table.TableConfigBean;
import com.gamma.bean.table.TableDetailBean;
import com.gamma.config.ModelGeneratorConfig;
import com.gamma.service.pageHtml.PageGenerator;
import com.gamma.service.pageHtml.impl.BootstrapStyleGenerator;
import com.gamma.service.pageHtml.impl.LayuiStyleGenerator;
import com.gamma.service.table.TableService;
import com.gamma.tools.DataSourceHelper;
import com.gamma.tools.FileTools;
import com.gamma.tools.ServiceCodeGeneratorUtil;
import org.springframework.util.StringUtils;

@Service
public class TableServiceImpl extends BaseService implements TableService{
	
	
	@Override
	public DatabaseBean connectDatabase(DatabaseBean bean) {
		try {
			DataSourceHelper.connectToDatabase(bean);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return bean;
	}
	
	
	@Override
	public List getList(DatabaseBean bean) {
		super.getLogger().info("getList");
		List<String> list = new ArrayList<String>();
		Connection connection = null;
		PreparedStatement pstate = null;
		try {
			
			connection =  DataSourceHelper.connectToDatabase(bean);
			pstate = connection.prepareStatement("show tables");
			ResultSet rs = pstate.executeQuery();    
			while(rs.next()){
				list.add(rs.getString(1));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				
				if(pstate != null){
					pstate.close();
				}
				
				if(connection != null){
					connection.close();
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return list;
	}

	@Override
	public List<TableDetailBean> getDetailByName(String tableName, DatabaseBean dbBean) {
		
		super.getLogger().info("getDetailByName tableName :" + tableName);
		
	
		Connection connection = null;
		List<TableDetailBean> list = null;
		try {
			
			connection =  DataSourceHelper.connectToDatabase(dbBean);
			list = this.fullToListByConnect(tableName, connection);
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				
				if(connection != null){
					connection.close();
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return list;
	}

	private List<TableDetailBean> fullToListByConnect(String tableName, Connection connection) {
		
		List<TableDetailBean> list = new ArrayList<TableDetailBean>();
		PreparedStatement pstate = null;
		try {
			
			String sql = "show full fields from "+ tableName;
			
			pstate = connection.prepareStatement(sql);
			ResultSet results = pstate.executeQuery(); 
			TableDetailBean bean = null;
			while(results.next()){
				bean = new TableDetailBean();
				bean.setFiled(results.getString("FIELD"));
				bean.setType(results.getString("Type"));
				bean.setComment(results.getString("Comment") == null ? "" : results.getString("Comment"));
				bean.setDef(results.getString("Default") == null ? "" : results.getString("Default"));
				bean.setIsNull(results.getString("Null"));
				bean.setLength(this.setLengthByType(results.getString("Type")));
				bean.processField();
				list.add(bean);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				
				if(pstate != null){
					pstate.close();
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return list;
	}


	private String setLengthByType(String type) {
		if(type.indexOf("(") != -1){
			return type.substring(type.indexOf("(")+1, type.indexOf(")")) ;
		}
		return "";
	}

	@Override
	public String startGeneratorModel(TableConfigBean bean, DatabaseBean dbBean) {
		ModelGeneratorConfig config = new ModelGeneratorConfig(bean);
		Connection connection = null;
		config.mkdir();
		
		super.getLogger().info("startGeneratorModel config.getPath() : " + config.getPath() + " config.getZipFileName() " + config.getZipFileName());

		//服务端代码生成
		try {
				connection =  DataSourceHelper.connectToDatabase(dbBean);
			
				for(String table : bean.getTableArr()){
					
					List<TableDetailBean> list = this.fullToListByConnect(table, connection);
					NameCollectionBean nameBean = new NameCollectionBean(table);
					nameBean.setListBean(list);
					config.setNameBean(nameBean);
				
					ServiceCodeGeneratorUtil.generatorTableToModel(config);
				
					this.switchPageGenerator(config);
			
				}
	
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				
				if(connection != null){
					connection.close();
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		//生成zip文件
		FileTools.fileToZip(config.getPath(), config.getZipFileName());
		//删除文件
		FileTools.deleteDir(new File(config.getPath()));
		
		return config.getZipFileName();
	
	}

	private void switchPageGenerator(ModelGeneratorConfig config) throws IOException {
		
		String pageCss = config.getPageCss();
		if(pageCss == null){
			return;
		}
		
		PageGenerator pageGenerator = null;
		if("bootstrap".equals(pageCss)) {
			pageGenerator = new BootstrapStyleGenerator(config);
			pageGenerator.toStart();
		}else if("layui".equals(pageCss)){
			pageGenerator = new LayuiStyleGenerator(config);
			pageGenerator.toStart();
		}
		
	}

}
