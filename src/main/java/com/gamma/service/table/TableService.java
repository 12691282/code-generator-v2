package com.gamma.service.table;

import java.util.List;

import com.gamma.bean.table.DatabaseBean;
import com.gamma.bean.table.TableConfigBean;
import com.gamma.bean.table.TableDetailBean;

public interface TableService {

	List getList(DatabaseBean bean);

	List<TableDetailBean> getDetailByName(String tableName, DatabaseBean dbBean);
	
	/**
	 * 开始生成模块
	 * @param bean
	 */
	String startGeneratorModel(TableConfigBean bean, DatabaseBean dbBean);

	DatabaseBean connectDatabase(DatabaseBean bean);

}
