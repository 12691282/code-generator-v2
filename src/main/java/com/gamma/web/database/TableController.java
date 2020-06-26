package com.gamma.web.database;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gamma.base.BaseController;
import com.gamma.bean.table.DatabaseBean;
import com.gamma.bean.table.TableConfigBean;
import com.gamma.service.table.TableService;
import com.gamma.tools.FileTools;

@Controller
@RequestMapping("table")
public class TableController extends BaseController{
	
	@Autowired
	private TableService tableService;
	
    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.url}")
    private String url;

	//基础路径
	@Value("${generateConfig.basePackage}")
	private String  basePackage;

	//基础模块名称
	@Value("${generateConfig.baseModelName}")
	private String  baseModelName;

	//数据层后缀名称(Dao 或 Mapper 或其他)
	@Value("${generateConfig.daoNameSuffix}")
	private String daoNameSuffix;

	// Y:生成 N:不生成
	@Value("${generateConfig.isBaseModel}")
	private String isBaseModel;

	//baseModel 路径
	@Value("${generateConfig.baseModelPath}")
	private String baseModelPath;


	@RequestMapping("connectDatabase")
	@ResponseBody
	public Map connectDatabase(HttpServletRequest request,DatabaseBean bean){
		
		bean = tableService.connectDatabase(bean);
		if(bean == null){
			return super.failureInfo("");
		}
		HttpSession session = request.getSession();
		session.setAttribute(session.getId(), bean);
		return super.successInfo("");
	}
	
	
	@RequestMapping("list")
	public String getList(Model model, HttpServletRequest request){
		
		DatabaseBean bean = super.getDataBase(request.getSession());
		
		if(bean == null){
			bean = new DatabaseBean();
			bean.setUrl(url);
			bean.setPassword(password);
			bean.setUsername(username);
			HttpSession session = request.getSession();
			session.setAttribute(session.getId(), bean);
		}

		TableConfigBean configInfo = new TableConfigBean();
		configInfo.setBaseModelName(baseModelName);
		configInfo.setBasePackage(basePackage);
		configInfo.setDaoNameSuffix(daoNameSuffix);
		configInfo.setIsBaseModel(isBaseModel);
		configInfo.setBaseModelPath(baseModelPath);


		List list = tableService.getList(bean);
		model.addAttribute("list", list);
		model.addAttribute("dbBean", bean);
		model.addAttribute("configInfo", configInfo);
		
		return "/table/list";
	}
	

	@RequestMapping("detail")
	public String getDetailByName(HttpServletRequest request,Model model, String tableName){
		DatabaseBean dbBean = super.getDataBase(request.getSession());
		if(dbBean != null){
			List list = tableService.getDetailByName(tableName,dbBean);
			model.addAttribute("list", list);
			model.addAttribute("tableName", tableName);
		}
		return "/table/detail";
	}
	
	@RequestMapping("startToGeneratorModel")
	public void startToGeneratorModel(HttpServletRequest request, HttpServletResponse response, TableConfigBean bean){
		super.getLogger().info("startToGeneratorModel bean" + bean);
		
		String strDirPath = request.getSession().getServletContext().getRealPath("/");
		super.getLogger().info("startToGeneratorModel strDirPath " + strDirPath);
		bean.setPath(strDirPath);    // 应用的web目录的名称
		DatabaseBean dbBean = super.getDataBase(request.getSession());
		
		String  filePath = tableService.startGeneratorModel(bean, dbBean);
		
		FileTools.downloadFIle(response, filePath, "GeneratorSystemFile.zip");
		FileTools.deleteDir(new File(filePath));
	    
	}
}
