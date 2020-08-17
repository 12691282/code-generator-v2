package com.gamma.web.database;

import com.gamma.base.BaseController;
import com.gamma.bean.DatabaseBean;
import com.gamma.service.entity.GeneratorConfigEntity;
import com.gamma.service.entity.GeneratorTableColumnEntity;
import com.gamma.service.entity.GeneratorTableInfoEntity;
import com.gamma.service.table.GeneratorService;
import com.gamma.tools.JdbcUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
		GeneratorConfigEntity config = generatorService.getConfigInfo();
		model.addAttribute("config", config);
		this.setPageDetail(model,list);
		return "/generator/list";
	}

	/**
	 * 保存配置
	 * @param config
	 * @return
	 */
	@PostMapping("saveConfig")
	@ResponseBody
	public Map saveConfig(GeneratorConfigEntity config){
		log.info("config {}", config);
		JdbcUtil.updateById(config);
		return super.successInfo("保存成功");
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

	/**
	 * 导入所选数据库表
	 * @param bean
	 * @return
	 */
	@PostMapping("importTableInfo")
	@ResponseBody
	public Map importTableInfo(DatabaseBean bean){
		log.info("bean {}", bean);
		generatorService.connectAndImport(bean);
		return super.successInfo("导入成功");
	}

	@GetMapping("tableInfoDetail")
	public String tableInfoDetail(Model model, String tableName){
		log.info("tableName {}", tableName);
 		GeneratorTableInfoEntity infoEntity = generatorService.getTableInfoDetail(tableName);
 		if(infoEntity == null){
            return "redirect:/generator/list";
        }
		List<GeneratorTableColumnEntity> columnList = generatorService.getTableColumnListById(infoEntity.getGeneratId());
		GeneratorConfigEntity config = generatorService.getConfigInfo();
		model.addAttribute("infoEntity", infoEntity);
		model.addAttribute("columnList", columnList);
		model.addAttribute("tableName", tableName);
		model.addAttribute("javaTypeList", config.getJavaTypeList());
        model.addAttribute("queryMethodList", config.getQueryMethodList());
        model.addAttribute("htmlInputTypeList", config.getHtmlInputTypeList());

		return "generator/tableInfoDetail";
	}

	@PostMapping("updateTableInfoEntity")
	@ResponseBody
	public Map updateTableInfoEntity(GeneratorTableInfoEntity entity){
		log.info("entity {}", entity);
		generatorService.updateTableInfoEntity(entity);
		return super.successInfo("保存成功");
	}


    @PostMapping("updateTableColumnInfo")
    @ResponseBody
    public Map updateTableColumnInfo(GeneratorTableColumnEntity entity){
        log.info("entity {}", entity);
        JdbcUtil.updateById(entity);
        return super.successInfo("保存成功");
    }

    /**
     * 生成和下载代码
     * @param model
     * @param tableNameArr
     */
    @PostMapping("toGeneratorAndDownload")
    public void toGeneratorAndDownload(HttpServletResponse response, String tableNameArr) throws IOException {
        log.info("tableNameArr {}", tableNameArr);
        String[] tableArr = tableNameArr.split(",");
		byte[] codeByte = generatorService.getGeneratorByte(tableArr);
		response.reset();
		response.setHeader("Content-Disposition", "attachment; filename=\"generatorCode.zip\"");
		response.addHeader("Content-Length", "" + codeByte.length);
		response.setContentType("application/octet-stream; charset=UTF-8");
		IOUtils.write(codeByte, response.getOutputStream());
    }


    private void setPageDetail(Model model, List list) {
		model.addAttribute("list", list);
		model.addAttribute("dataBaseUrl", dataBaseUrl);
		model.addAttribute("dataBaseDriver", dataBaseDriver);
		model.addAttribute("dataBaseUsername", dataBaseUsername);
		model.addAttribute("dataBasePassword", dataBasePassword);
	}

}
