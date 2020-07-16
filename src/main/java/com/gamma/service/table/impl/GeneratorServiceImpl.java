package com.gamma.service.table.impl;

import com.gamma.base.BaseService;
import com.gamma.bean.table.DatabaseBean;
import com.gamma.service.table.GeneratorService;
import com.gamma.tools.DataSourceHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;



@Service
public class GeneratorServiceImpl  extends BaseService implements GeneratorService {

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.driver}")
    private String driver;



    @Override
    public List getList() {
        DatabaseBean bean =  new DatabaseBean();
        bean.setUrl(url);
        bean.setPassword(password);
        bean.setUsername(username);
        bean.setDriver(driver);
        try {
            DataSourceHelper.connectToDatabase(bean);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
//
}
