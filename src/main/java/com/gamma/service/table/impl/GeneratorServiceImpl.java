package com.gamma.service.table.impl;

import com.gamma.base.BaseService;
import com.gamma.service.entity.GeneratorTableInfoEntity;
import com.gamma.service.table.GeneratorService;
import org.springframework.stereotype.Service;

import java.util.List;



@Service
public class GeneratorServiceImpl  extends BaseService implements GeneratorService {





    @Override
    public List getList() {
        List<GeneratorTableInfoEntity>  list =  super.queryList(GeneratorTableInfoEntity.class);
        return list;
    }
}
