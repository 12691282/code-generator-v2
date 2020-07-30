package com.gamma.service.table;

import com.gamma.bean.table.DatabaseBean;

import java.util.List;

public interface GeneratorService {
    //获取需要生成的页面列表
    List getList();

    /**
     * 链接目标数据库
     * @param bean
     * @return
     */
    List connectTargetDataBase(DatabaseBean bean);

    /**
     * 链接数据库和导入数据
     * @param bean
     */
    void connectAndImport(DatabaseBean bean);
}
