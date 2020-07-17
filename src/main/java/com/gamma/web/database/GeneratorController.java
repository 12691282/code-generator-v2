package com.gamma.web.database;

import com.gamma.base.BaseController;
import com.gamma.bean.table.DatabaseBean;
import com.gamma.bean.table.TableConfigBean;
import com.gamma.service.table.GeneratorService;
import com.gamma.service.table.TableService;
import com.gamma.tools.FileTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("generator")
public class GeneratorController extends BaseController{
	
	@Autowired
	private GeneratorService generatorService;
	
	@RequestMapping("list")
	public String getList(Model model){
		List list = generatorService.getList();
		model.addAttribute("list", list);
		return "/generator/list";
	}

}
