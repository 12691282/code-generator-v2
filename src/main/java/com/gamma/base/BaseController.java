package com.gamma.base;

import com.gamma.bean.DatabaseBean;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class BaseController {

	protected Map failureInfo(Object data) {
		Map info = new HashMap();
		info.put("info", "0");
		info.put("data", data);
		return info;
	}
	
	protected Map successInfo(Object data) {
		Map info = new HashMap();
		info.put("info", "1");
		info.put("data", data);
		return info;
	}

	public DatabaseBean getDataBase(HttpSession session) {
		return (DatabaseBean) session.getAttribute(session.getId());
	}
	
}
