package com.gamma.service.pageHtml.impl;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import org.apache.log4j.Logger;

import com.gamma.bean.code.NameCollectionBean;
import com.gamma.bean.table.TableDetailBean;
import com.gamma.config.ModelGeneratorConfig;
import com.gamma.generator.mybatis.GeneratorToHtmlHelper;
import com.gamma.service.pageHtml.PageGenerator;
import com.gamma.tools.CommonForPageConfig;
import com.gamma.tools.FileTools;
import com.gamma.tools.ServiceCodeGeneratorUtil;

public class BootstrapStyleGenerator implements PageGenerator{
	
	protected Logger logger =  Logger.getLogger(BootstrapStyleGenerator.class);
	
	private ModelGeneratorConfig config;

	public BootstrapStyleGenerator(ModelGeneratorConfig config) {
	    this.config = config;
	}

	private static final String input_size_style = "height:30px;";
	
	@Override
	public void toStart() {
	
		
		//生成list jsp页面
		this.generatorHtmlListPage();
		
		//生成info jsp页面
		this.generatorHtmlInfoPage();
		
	}
	
	/**
	 * 页面列表数据
	 */
	private void generatorHtmlListPage() {
		BufferedWriter bw = null;
		try {
			
			NameCollectionBean nameBean = this.config.getNameBean();
			String fileAddr = this.config.getPath() +"/"+ CommonForPageConfig.ROOT_PAGE_NAME + "/"+ nameBean.getLowerHeadWordClassName();
			File filePath = new File(fileAddr);
			if(!filePath.exists()){
				filePath.mkdirs();
			}
			
			File file = new File(fileAddr,CommonForPageConfig.LIST_PAGE_NAME+".jsp");
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
			
			this.toGeneratorHtmlHeadPart(bw);
			this.toGeneratorHtmlBodyPart(bw);
			this.toGeneratorHtmlJavascriptPart(bw);
			
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				bw.flush();
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
	/**
	 * 页面弹出详情
	 */
	private void generatorHtmlInfoPage() {
		BufferedWriter bw = null;
		try {
			
			NameCollectionBean nameBean = this.config.getNameBean();
			List<TableDetailBean> list =  nameBean.getListBean();
			
			String fileAddr = this.config.getPath() +"/"+ CommonForPageConfig.ROOT_PAGE_NAME + "/"+ nameBean.getLowerHeadWordClassName();
			File file = new File(fileAddr,CommonForPageConfig.INFO_PAGE_NAME+".jsp");
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
			
			GeneratorToHtmlHelper.toGeneratorHeadPart(bw);
			bw.newLine();
			bw.write("<div class=\"modal-dialog\"><!-- dialog start -->");
			bw.newLine();
			bw.write("\t<div class=\"modal-content\" > <!-- content start -->");
			bw.newLine();
			boolean printTail = false;
			for(int i=0,size=list.size(); i<size; i++){
				
				TableDetailBean bean = list.get(i);
				
				if("id".equals(bean.getFiled())){
					bw.write("\t\t<input name=\"id\" value=\"${"+nameBean.getLowerHeadWordClassName()+".id}\" type=\"hidden\" />");
					bw.newLine();
					continue;
				}
				
				if((i-1)%2 == 0){
					bw.write("\t\t<div class=\"control-group\">");
				}else{
					printTail = true;
				}
				
				String timeType = "";
				if(bean.isDateTypeField()){
					timeType = this.getTimeInputEl();
				} 
				
				bw.newLine();
				bw.write("\t\t\t" + bean.getComment() + " :<input class=\"form-control\" style=\""+input_size_style+"\" name=\"" + 
						bean.getCodeField() + "\"  value=\"${"+nameBean.getLowerHeadWordClassName()+"." + bean.getCodeField() + "}\" "+
						timeType + " type=\"text\" placeholder=\"请输入" +
						bean.getComment() + "\" maxlength=\""+bean.getLength()+"\"/>" );
				
				
				if(printTail){
					bw.newLine();
					bw.write("\t\t</div>");
					printTail = false;
				}
				bw.newLine();
			}
			bw.newLine();
			bw.write("\t</div><!-- /.modal-content end-->");
			bw.newLine();
			bw.write("</div><!-- /.modal end-->");
			bw.newLine();
			
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				bw.flush();
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
	}

	/**
	 * 生成页面头部信息
	 * @param nameBane
	 * @throws IOException 
	 */
	private BufferedWriter toGeneratorHtmlHeadPart(BufferedWriter bw) throws IOException {
		NameCollectionBean nameBean = this.config.getNameBean();
		
		GeneratorToHtmlHelper.toGeneratorHeadPart(bw);
		
		bw.write("<html>");
		bw.newLine();
		bw.write("<head>");
		bw.newLine();
		bw.write("\t<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
		bw.newLine();
		bw.write("\t<title>"+nameBean.getLowerHeadWordClassName()+"页面</title>");
		bw.newLine();
		bw.write("\t<link rel=\"stylesheet\" href=\"/js/plus/bootstrap/css/bootstrap.min.css\"  media=\"screen\">");
		bw.newLine();
		bw.write("\t<link rel=\"stylesheet\" href=\"/js/plus/bootstrap/css/bootstrap-responsive.min.css\" >");
		bw.newLine();
		bw.write("\t<!-- js 加载 -->");
		bw.newLine();
		bw.write("\t<script type=\"text/javascript\" src=\"/js/plus/jquery/jquery-1.12.4.min.js\"></script>");
		bw.newLine();
		bw.write("\t<script type=\"text/javascript\" src=\"/js/plus/jquery/jquery.form.min.js\"></script>");
		bw.newLine();
		bw.write("\t<script type=\"text/javascript\" src=\"/js/plus/bootstrap/js/bootstrap.min.js\"></script>");
		bw.newLine();
		bw.write("\t<script type=\"text/javascript\" src=\"/js/plus/bootstrap/js/tip.js\"></script>");
		bw.newLine();
		bw.write("\t<script type=\"text/javascript\" src=\"/js/plus/My97DatePicker/WdatePicker.js\"></script>");
		bw.newLine();
		bw.write("</head>");
		bw.newLine();
		return bw;
		
	}
	
	/**
	 * html body 页面生成
	 * @param bw
	 * @throws IOException
	 */
	private void toGeneratorHtmlBodyPart(BufferedWriter bw) throws IOException {
			List<TableDetailBean> list =  this.config.getNameBean().getListBean();
		
			bw.newLine();
			bw.write("<body>");
			bw.newLine();
			bw.write("<!-- container start -->");
			bw.newLine();
			bw.write("\t<div class=\"container\">");
			bw.newLine();
			bw.write("\t\t<legend> </legend>");
			bw.newLine();
			bw.write("\t\t<form class=\"form-inline\" role=\"form\" action=\""+CommonForPageConfig.LIST_METHOD_URL+"\" method=\"post\">");
			bw.newLine();
			for(int i=0;i<list.size(); i++){
				
				TableDetailBean bean = list.get(i);
				
				if("id".equals(bean.getFiled())){
					continue;
				}
				
				String isDateType = "";
				if(bean.isDateTypeField()){
					isDateType = this.getTimeInputEl();
					
					bw.write("\t\t\t" + bean.getComment() + "开始 :<input class=\"form-control\" style=\""+input_size_style+"\" name=\"" + 
							bean.getCodeFieldForStartTime() + "\" value=\"${"+CommonForPageConfig.QUERY_PAGE_OBJECT_NAME+"." +
							bean.getCodeFieldForStartTime() + "}\" type=\"text\" "+ isDateType +" placeholder=\"请输入" +
							bean.getComment() + "开始\" />-" );
					bw.newLine();
					bw.write("\t\t\t" + bean.getComment() + "结束 :<input class=\"form-control\" style=\""+input_size_style+"\" name=\"" + 
							bean.getCodeFieldForEndTime() + "\" value=\"${"+CommonForPageConfig.QUERY_PAGE_OBJECT_NAME+"." +
							bean.getCodeFieldForEndTime() + "}\" type=\"text\" "+ isDateType +" placeholder=\"请输入" +
							bean.getComment() + "结束\"  />" );
					bw.newLine();
					continue;
				}
				
				bw.write("\t\t\t" + bean.getComment() + " :<input class=\"form-control\" style=\""+input_size_style+"\" name=\"" + 
						bean.getCodeField() + "\" value=\"${"+CommonForPageConfig.QUERY_PAGE_OBJECT_NAME+"." +
						bean.getCodeField() + "}\" type=\"text\" "+ isDateType +" placeholder=\"请输入" +
						bean.getComment() + "\" maxlength=\""+bean.getLength()+"\"/>" );
				bw.newLine();
				if(i % 3 == 0){
					bw.write("\t\t\t<br/><br/>");
					bw.newLine();
				}
			}
			bw.write("\t\t\t<button class=\"btn btn-success query\">查询</button>");
			bw.newLine();
			bw.write("\t\t\t<button class=\"btn btn-primary reset\">重置</button>");
			bw.newLine();
			bw.write("\t\t</form>");
			bw.newLine();
			bw.write("\t\t<legend></legend>");
			bw.newLine();
			bw.write("\t\t<div>");
			bw.newLine();
			bw.write("\t\t\t<button class=\"btn btn-inverse\" id=\"addModel\">新增</button>");
			bw.newLine();
			bw.write("\t\t</div>");
			bw.newLine();
			bw.write("\t\t<table class=\"table table-hover\">");
			bw.newLine();
			bw.write("\t\t\t<thead>");
			bw.newLine();
			bw.write("\t\t\t\t<tr>");
			bw.newLine();
			bw.write("\t\t\t\t\t<th></th>");
			bw.newLine();
			for(TableDetailBean beanTh : list){
				
				if("id".equals(beanTh.getFiled())){
					bw.write("\t\t\t\t\t<th>序号</th>");
					bw.newLine();
					continue;
				}
				bw.write("\t\t\t\t\t<th>"+ beanTh.getComment()+"</th>");
				bw.newLine();
			}
			bw.write("\t\t\t\t\t<th>操作</th>");
			bw.newLine();
			bw.write("\t\t\t\t</tr>");
			bw.newLine();
			bw.write("\t\t\t</thead>");
			bw.newLine();
			bw.newLine();
			bw.write("\t\t\t<tbody>");
			bw.newLine();
			bw.write("\t\t\t\t<c:forEach items=\"${"+CommonForPageConfig.DATA_PAGE_OBJECT_NAME+".list}\" var=\"v\" varStatus=\"status\">");
			bw.newLine();
			bw.write("\t\t\t\t\t<tr>");
			bw.newLine();
			bw.write("\t\t\t\t\t\t<td><input type=\"checkbox\" value=\"${v}\"></td>");
			bw.newLine();
			for(TableDetailBean beanTh : list){
				if(beanTh.isDateTypeField()){
					bw.write("\t\t\t\t\t\t<td><fmt:formatDate value=\"${v."+ beanTh.getCodeField()+"}\" pattern=\"yyyy-MM-dd HH:mm:ss\"/></td>");
					bw.newLine();
					continue;
				}
				bw.write("\t\t\t\t\t\t<td>${v."+ beanTh.getCodeField()+"}</td>");
				bw.newLine();
			}
			bw.write("\t\t\t\t\t\t<td>");
			bw.newLine();
			bw.write("\t\t\t\t\t\t\t<button class=\"btn btn-success\" type=\"button\" value=\"${v.id}\">修改</button>&nbsp;");
			bw.newLine();
			bw.write("\t\t\t\t\t\t\t<button class=\"btn btn-danger\" type=\"button\" value=\"${v.id}\">删除</button>");
			bw.newLine();
			bw.write("\t\t\t\t\t\t</td>");
			bw.newLine();
			bw.write("\t\t\t\t\t<tr>");
			bw.newLine();
			bw.write("\t\t\t\t</c:forEach>");
			bw.newLine();
			bw.write("\t\t\t</tbody>");
			bw.newLine();
			bw.write("\t\t</table>");
			bw.newLine();
			bw.write("\t\t<hr>");
			bw.newLine();
			bw.write("\t\t"+CommonForPageConfig.PAGER_JSP_INCLUD_FONFIG);
			bw.newLine();
			ServiceCodeGeneratorUtil.outputHtmlPageAuthor(bw);
			bw.newLine();
			bw.write("\t</div>");
			bw.newLine();
			bw.newLine();
			bw.newLine();
			bw.newLine();
			bw.write("<!-- model start -->");
			bw.newLine();
			bw.write("\t<div class=\"modal fade\" id=\"infoModal\" tabindex=\"-1\" role=\"dialog\" style=\"width: 700px;\" aria-hidden=\"true\">");
			bw.newLine();
			bw.write("\t<form action=\"addOrModify\" method=\"post\">");
			bw.newLine();
			bw.write("\t\t<div class=\"modal-dialog\">");
			bw.newLine();
			bw.write("\t\t\t<div class=\"modal-content\">");
			bw.newLine();
			bw.write("\t\t\t\t<div class=\"modal-header\">");
			bw.newLine();
			bw.write("\t\t\t\t\t<button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\">&times;</button>");
			bw.newLine();
			bw.write("\t\t\t\t\t<h4 class=\"modal-title\" id=\"myModalLabel\">信息</h4>");
			bw.newLine();
			bw.write("\t\t\t\t</div>");
			bw.newLine();
			bw.write("\t\t\t\t<div class=\"modal-body form-horizontal\">");
			bw.newLine();
			bw.write("\t\t\t\t</div>");
			bw.newLine();
			bw.write("\t\t\t</div>");
			bw.newLine();
			
			bw.write("\t\t\t<div class=\"modal-footer\">");
			bw.newLine();
			bw.write("\t\t\t\t<button type=\"button\" class=\"btn btn-default\" data-dismiss=\"modal\">关闭</button>");
			bw.newLine();
			bw.write("\t\t\t\t<button type=\"button\" class=\"btn btn-primary\" data-dismiss=\"modal \" id=\"addOrUpdateModel\" >确定</button>");
			bw.newLine();
			bw.write("\t\t\t</div>");
			bw.newLine();
			
			bw.write("\t\t</div>");
			bw.newLine();
			bw.write("\t</form>");
			bw.newLine();
			bw.write("\t</div>");
			bw.newLine();
			bw.write("<!-- model end -->");
			bw.newLine();
			bw.write("</body>");
			bw.newLine();
		 
	}
	
	private String getTimeInputEl() {
		return "onClick=\"WdatePicker({dateFmt:'yyyy-MM-dd',lang:'zh-cn',readOnly:true})\"";
	}

	
	/**
	 * html body js脚本生成
	 * @param bw
	 * @throws IOException
	 */
	private void toGeneratorHtmlJavascriptPart(BufferedWriter bw) throws IOException{
		logger.info("toGeneratorHtmlJavascriptPart");
		//获取类加载的根路径   resources下路径
	    FileTools.readFileToWriter("classpath:text/bootstrap_page_js.txt", bw);
	}

	
}
