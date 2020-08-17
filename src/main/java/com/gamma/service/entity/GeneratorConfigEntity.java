package com.gamma.service.entity;

import com.gamma.annotation.Column;
import com.gamma.annotation.PrimaryKey;
import com.gamma.annotation.Table;
import com.gamma.common.TypeConstants;
import com.gamma.service.mapper.TableColumnMapper;
import com.gamma.service.vo.GeneratorTableColumnVO;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Table("generator_config_info")
@Data
public class GeneratorConfigEntity {

    //编号
    @Column("id")
    @PrimaryKey
    private String id;

    //包路径
    @Column("base_package")
    private String basePackage;

    //模块名
    @Column("base_model_name")
    private String baseModelName;

    //作者名
    @Column("author_name")
    private String authorName;

    //不显示字段
    @Column("no_show_filed")
    private String noShowFiled;

    //java类型
    @Column("java_type_config")
    private String javaTypeConfig;

    //查询方法
    @Column("query_method_config")
    private String queryMethodConfig;

    //页面输入类型
    @Column("html_input_type_config")
    private String htmlInputTypeConfig;

    public String[] getJavaTypeList() {
        return this.getJavaTypeConfig() == null ? new String[]{} : this.getJavaTypeConfig().split(TypeConstants.SPLIT_SYMBOL);
    }

    public String[] getQueryMethodList() {
        return this.getQueryMethodConfig() == null ? new String[]{} : this.getQueryMethodConfig().split(TypeConstants.SPLIT_SYMBOL);
    }

    public String[] getHtmlInputTypeList() {
        return this.getHtmlInputTypeConfig() == null ? new String[]{} : this.getHtmlInputTypeConfig().split(TypeConstants.SPLIT_SYMBOL);
    }

    public String[] getNoShowFiledList() {
        return this.getHtmlInputTypeConfig() == null ? new String[]{} : this.getHtmlInputTypeConfig().split(TypeConstants.SPLIT_SYMBOL);
    }
}
