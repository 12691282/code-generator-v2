package com.gamma.service.pageHtml.impl;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Logger;

import com.gamma.bean.code.NameCollectionBean;
import com.gamma.bean.table.TableDetailBean;
import com.gamma.config.ModelGeneratorConfig;
import com.gamma.generator.mybatis.GeneratorToHtmlHelper;
import com.gamma.service.pageHtml.PageGenerator;
import com.gamma.tools.CommonForPageConfig;
import com.gamma.tools.FileTools;
import com.gamma.tools.ServiceCodeGeneratorUtil;

@Slf4j
public class ElementUIStyleGenerator implements PageGenerator{
	
	private ModelGeneratorConfig config;

	public ElementUIStyleGenerator(ModelGeneratorConfig config) {
	    this.config = config;
	}

	private static final String input_size_style = "height:30px;";
	
	@Override
	public void toStart() {
		//生产目录
		this.dirFilePath();
		//生成信息主页面
		this.generateMainInfoPage();
		//生成修改页面
		this.generateEditInfoPage();
	}

	private void dirFilePath() {

		NameCollectionBean nameBean = this.config.getNameBean();
		String fileAddr = this.config.getPath() +"/"+ CommonForPageConfig.ROOT_PAGE_NAME + "/"+ nameBean.getLowerHeadWordClassName();
		File filePath = new File(fileAddr);
		if(!filePath.exists()){
			filePath.mkdirs();
		}
	}

	/**
	 * 主页面信息
	 */

	private static final String MAIN_HEAD_PATH_NAME = "vue/main/head";

	private static final String MAIN_TAIL_PATH_NAME = "vue/main/tail";

	/**
	 * 编辑页面信息
	 */
	private void generateMainInfoPage() {

		BufferedWriter bw = null;
		try {
			NameCollectionBean nameBean = this.config.getNameBean();
			String fileAddr = this.config.getPath() +"/"+ CommonForPageConfig.ROOT_PAGE_NAME + "/"+ nameBean.getLowerHeadWordClassName();
			File file = new File(fileAddr,nameBean.getLowerHeadWordClassName()+".vue");
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
			this.loadPageTemplateByName(MAIN_HEAD_PATH_NAME, bw);
			bw.newLine();
			bw.write("\t\t\t<el-table ref=\"multipleTable\" :data=\"tableData\" class=\"module-table\" border size=\"mini\" highlight-current-row @current-change=\"handleCurrentChange\">");
			bw.newLine();
			bw.newLine();


			for(TableDetailBean tableDetail : nameBean.getListBean()){
				String comment= tableDetail.getComment();
				Integer minWidth = 100;
				if(comment.contains("：") ){
					comment = comment.split("：")[0];

				} else if(comment.contains(":")){
					comment = comment.split(":")[0];
				}

				if(comment.length() > 2 && comment.length() < 4){
					minWidth = 120;
				}

				if(comment.length() > 4 && comment.length() < 6){
					minWidth = 140;
				}

				if(comment.length() > 6){
					minWidth = 180;
				}
				bw.write("\t\t\t\t<el-table-column prop=\""+tableDetail.getCodeField()+"\" label=\""+comment+"\" min-width=\""+minWidth+"\" align=\"center\">");
				bw.newLine();
			}

			bw.newLine();
			bw.write("\t\t\t</el-table>");
			bw.newLine();
			bw.write("\t\t\t<table-pagination :page=\"pageInfo\" :total=\"total\" @change=\"handlePaginationChange\"></table-pagination>");
			bw.newLine();
			bw.write("\t\t</el-card>");
			bw.newLine();
			String editLabel = nameBean.getLowerHeadWordClassName()+"-add-modal";
			bw.write("\t\t<"+editLabel+ " :visible.sync=\"addDialog\" :dataForm=\"dataForm\" @reloadList=\"queryAndResetList\">");
			bw.newLine();
			bw.write("\t\t</"+editLabel+">");
			bw.newLine();
			bw.write("</div>");
			bw.newLine();
			bw.write("</template>");
			bw.newLine();
			bw.write("<script>");
			bw.newLine();
			bw.write("import "+nameBean.getEditPageName()+"  from './"+nameBean.getEditPageName()+"';");
			bw.newLine();
			bw.write("import tablePagination from '@/components/table-pagination';");
			bw.newLine();
			bw.write("export default {");
			bw.newLine();
			bw.write("\tcomponents: {");
			bw.newLine();
			bw.write("\t\t"+nameBean.getEditPageName()+ ",");
			bw.newLine();
			bw.write("\t\ttablePagination");
			bw.newLine();
			bw.write("\t},");
			bw.newLine();
			this.loadPageTemplateByName(MAIN_TAIL_PATH_NAME, bw);

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


	private static final String EDIT_TAIL_PATH_NAME = "vue/edit/tail";

	private void generateEditInfoPage() {

		BufferedWriter bw = null;
		try {
			NameCollectionBean nameBean = this.config.getNameBean();
			String fileAddr = this.config.getPath() +"/"+ CommonForPageConfig.ROOT_PAGE_NAME + "/"+ nameBean.getLowerHeadWordClassName();
			File file = new File(fileAddr,nameBean.getEditPageName()+".vue");
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
			bw.write("<template>");
			bw.newLine();
			bw.write("\t<el-dialog :title=\"title\" v-if=\"visible\" :visible=\"visible\" :append-to-body=\"true\" :close-on-click-modal=\"false\" width=\"40%\" @close=\"close\">");
			bw.newLine();
			bw.write("\t\t<el-form ref=\"form\" :model=\"form\" :rules=\"rules\" label-width=\"130px\" size=\"small\">");
			bw.newLine();

			for(TableDetailBean tableDetail : nameBean.getListBean()){

				String comment= tableDetail.getComment();
				if(comment.contains("：") ){
					comment = comment.split("：")[0];

				} else if(comment.contains(":")){
					comment = comment.split(":")[0];
				}
				bw.write("\t\t\t<el-form-item label=\""+comment+"\" prop=\""+tableDetail.getCodeField()+"\">");
				bw.newLine();
				bw.write("\t\t\t\t<el-input class=\"module-input\" v-model=\"form."+tableDetail.getCodeField()+"\" clearable size=\"mini\"></el-input>");
				bw.newLine();
				bw.write("\t\t\t</el-form-item>");
				bw.newLine();
			}

			this.loadPageTemplateByName(EDIT_TAIL_PATH_NAME, bw);


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
	 * 根据名称读取模板
	 * @param bw
	 * @throws IOException
	 */
	private void loadPageTemplateByName(String name, BufferedWriter bw) throws IOException{
		log.info("toGeneratorHtmlJavascriptPart");
		String path = "classpath:text/"+name+".txt";
		//获取类加载的根路径   resources下路径
		FileTools.readFileToWriter(path, bw);
	}
}
