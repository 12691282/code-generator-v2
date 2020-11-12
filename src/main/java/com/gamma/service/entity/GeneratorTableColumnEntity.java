package com.gamma.service.entity;

import com.gamma.annotation.Column;
import com.gamma.annotation.PrimaryKey;
import com.gamma.annotation.Table;
import com.gamma.common.TypeConstants;
import lombok.Data;

@Table("generator_table_column")
@Data
public class GeneratorTableColumnEntity {

    //编号
    @Column("column_id")
    @PrimaryKey
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

    // 列类型
    @Column("column_type")
    private String columnType;

    //JAVA字段类型
    @Column("java_type")
    private String javaType;

    //JAVA字段名
    @Column("java_field")
    private String javaField;

    //是否主键（1是）
    @Column("is_pk")
    private String isPk;

    //是否自增（1是）
    @Column("is_increment")
    private String isIncrement;

    //是否必填（1是）
    @Column("is_required")
    private String isRequired;

    //是否列表展示字段（1是）
    @Column("is_list_show")
    private String isListShow;

    //其它生成选项 是否编辑
    @Column("is_edit")
    private String isEdit;

    /**是否查询字段**/
    @Column("is_query")
    private String isQuery;

    /**是否日期字段**/
    @Column("is_date")
    private String isDate;


    //查询方式（等于、不等于、大于、小于、范围）
    @Column("query_type")
    private String queryType;

    //显示类型（文本框、文本域、下拉框、复选框、单选框、日期控件）
    @Column("html_type")
    private String htmlType;

    //排序
    @Column("sort")
    private String sort;

    public boolean isPrimaryKey() {
        if(this.getIsPk() != null && this.getIsPk().equals(TypeConstants.REQUIRE)){
            return true;
        }
        return false;
    }

}
