package com.gamma.service.entity;

import com.gamma.annotation.Column;
import com.gamma.annotation.Table;
import lombok.Data;

@Table("generator_table_column")
@Data
public class GeneratorTableColumnEntity {

    //编号
    @Column("column_id")
    private String columnId;

    //归属表编号
    @Column("table_id")
    private String tableId;

    //列名称
    @Column("column_name")
    private String columnName;

    //列描述
    @Column("column_comment")
    private String columnComment;

    //JAVA字段名
    @Column("java_type")
    private String javaType;

    //是否主键（1是）
    @Column("is_pk")
    private String isPk;

    //是否自增（1是）
    @Column("is_increment")
    private String isIncrement;

    //是否必填（1是）
    @Column("is_required")
    private String isRequired;

    //生成功能作者
    @Column("is_insert")
    private String isInsert;

    //其它生成选项
    @Column("is_edit")
    private String isEdit;

    //备注
    @Column("is_query")
    private String isQuery;

    //查询方式（等于、不等于、大于、小于、范围）
    @Column("query_type")
    private String queryType;

    //查询方式（等于、不等于、大于、小于、范围）
    @Column("query_type")
    private String html_type;

    //排序
    @Column("sort;")
    private String sort;
}
