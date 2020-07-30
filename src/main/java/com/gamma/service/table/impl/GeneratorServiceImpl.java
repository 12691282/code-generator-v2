package com.gamma.service.table.impl;

import com.gamma.base.BaseService;
import com.gamma.bean.table.DatabaseBean;
import com.gamma.service.entity.GeneratorTableInfoEntity;
import com.gamma.service.table.GeneratorService;
import com.gamma.tools.JdbcUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


/***
 *  使用模板生成代码
 * v2
 */

@Service
public class GeneratorServiceImpl  extends BaseService implements GeneratorService {


    @Override
    public List getList() {
        List<GeneratorTableInfoEntity>  list =  JdbcUtil.queryList(GeneratorTableInfoEntity.class);
        return list;
    }

    @Override
    public List connectTargetDataBase(DatabaseBean bean) {
        return JdbcUtil.connectAndGet(bean);
    }
}
