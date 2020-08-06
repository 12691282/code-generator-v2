package com.gamma.service.entity;

import com.gamma.annotation.Column;
import com.gamma.annotation.PrimaryKey;
import com.gamma.annotation.Table;
import lombok.Data;

@Table("generator_table_info")
@Data
public class GeneratorTableInfoEntity {

    //编号
    @Column("generat_id")
    @PrimaryKey
    private String generatId;
    //表名称
    @Column("table_name")
    private String tableName;

    //表描述
    @Column("table_comment")
    private String tableComment;

    //实体类名称
    @Column("class_name")
    private String className;

    //生成包路径
    @Column("package_name")
    private String packageName;

    //生成模块名
    @Column("module_name")
    private String moduleName;

    //生成业务名
    @Column("business_name")
    private String businessName;

    //生成功能名
    @Column("function_name")
    private String functionName;

    //生成功能作者
    @Column("function_author")
    private String functionAuthor;

    //其它生成选项
    @Column("options")
    private String options;

    //备注
    @Column("remark")
    private String remark;

    //备注
    @Column("create_time")
    private String createTime;


}
