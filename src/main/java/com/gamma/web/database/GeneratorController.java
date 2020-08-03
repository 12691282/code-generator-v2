package com.gamma.web.database;

import com.gamma.base.BaseController;
import com.gamma.bean.table.DatabaseBean;
import com.gamma.service.entity.GeneratorTableInfoEntity;
import com.gamma.service.table.GeneratorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("generator")
@Slf4j
public class GeneratorController extends BaseController{
	
	@Autowired
	private GeneratorService generatorService;


	//目标数据库配置
	@Value("${generateConfig.target.dataBase.url}")
	private String dataBaseUrl;

	//目标数据库驱动
	@Value("${generateConfig.target.dataBase.driver}")
	private String dataBaseDriver;

	//目标数据库用户名
	@Value("${generateConfig.target.dataBase.username}")
	private String dataBaseUsername;

	//目标数据库密码
	@Value("${generateConfig.target.dataBase.password}")
	private String dataBasePassword;
	
	@RequestMapping("list")
	public String getList(Model model){
		log.info("get List");
		List list = generatorService.getList();
		this.setPageDetail(model,list);
		return "/generator/list";
	}

	@PostMapping("connectTargetDataBase")
	public String connectTargetDataBase(Model model, DatabaseBean bean){
		log.info("bean {}", bean);
		List<GeneratorTableInfoEntity> list = generatorService.connectTargetDataBase(bean);
		if(list == null){
			return "redirect:/generator/list";
		}
		this.setPageDetail(model,list);
		return "generator/targetDatabaseList";
	}
	@PostMapping("importTableInfo")
	public Map importTableInfo(DatabaseBean bean){
		log.info("bean {}", bean);

		generatorService.connectAndImport(bean);

		return super.successInfo("导入成功");
	}



	private void setPageDetail(Model model, List list) {
		model.addAttribute("list", list);
		model.addAttribute("dataBaseUrl", dataBaseUrl);
		model.addAttribute("dataBaseDriver", dataBaseDriver);
		model.addAttribute("dataBaseUsername", dataBaseUsername);
		model.addAttribute("dataBasePassword", dataBasePassword);
	}

}
