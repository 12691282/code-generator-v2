package com.gamma.service.table;

import com.gamma.bean.DatabaseBean;
import com.gamma.service.entity.GeneratorConfigEntity;
import com.gamma.service.entity.GeneratorTableColumnEntity;
import com.gamma.service.entity.GeneratorTableInfoEntity;

import java.util.List;

public interface GeneratorService {
    //获取需要生成的页面列表
    List getList();

    /**
     * 链接目标数据库
     * @param bean
     * @return
     */
    List<GeneratorTableInfoEntity> connectTargetDataBase(DatabaseBean bean);

    /**
     * 链接数据库和导入数据
     * @param bean
     */
    void connectAndImport(DatabaseBean bean);

    GeneratorTableInfoEntity getTableInfoDetail(String tableName);

    List<GeneratorTableColumnEntity> getTableColumnListById(String generatId);

    void updateTableInfoEntity(GeneratorTableInfoEntity entity);

    /**
     * 生成字节数组
     * @param tableArr
     * @return
     */
    byte[] getGeneratorByte(String[] tableArr);

    GeneratorConfigEntity getConfigInfo();
}
