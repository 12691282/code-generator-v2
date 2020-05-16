package com.gamma.service.pageHtml.impl;


import java.util.List;

import com.gamma.bean.code.NameCollectionBean;
import com.gamma.bean.table.TableDetailBean;
import com.gamma.config.ModelGeneratorConfig;
import com.gamma.service.pageHtml.PageGenerator;

public class LayuiStyleGenerator implements PageGenerator{
	
	private ModelGeneratorConfig config;

	public LayuiStyleGenerator(ModelGeneratorConfig config) {
	    this.config = config;
	}

	@Override
	public void toStart() {
		NameCollectionBean nameBane = this.config.getNameBean();
		List<TableDetailBean> list = nameBane.getListBean();
		System.out.println(nameBane);
	}

}
