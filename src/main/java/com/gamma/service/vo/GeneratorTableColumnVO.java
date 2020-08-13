package com.gamma.service.vo;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@Data
public class GeneratorTableColumnVO {

    //编号
    private String columnId;

    //归属表编号
    private String tableId;

    //列名称
    private String columnName;

    //列描述
    private String columnComment;

    // 列类型
    private String columnType;

    //JAVA字段类型
    private String javaType;

    //JAVA字段名
    private String javaField;

    //是否主键（1是）
    private String isPk;

    //是否自增（1是）
    private String isIncrement;

    //是否必填（1是）
    private String isRequired;

    //是否列表展示字段（1是）
    private String isListShow;

    //其它生成选项 是否编辑
    private String isEdit;
    //备注
    private String isQuery;

    //查询方式（等于、不等于、大于、小于、范围）
    private String queryType;

    //显示类型（文本框、文本域、下拉框、复选框、单选框、日期控件）
    private String htmlType;

    //排序
    private String sort;

    public boolean isPrimaryKey() {
        if(this.getIsPk() != null && this.getIsPk().equals("1")){
            return true;
        }
        return false;
    }

    public boolean isNoEdit() {
        if(this.getIsEdit() != null && this.getIsEdit().equals("1")){
            return true;
        }
        return false;
    }

    public boolean isQueryField() {
        if(this.getIsQuery() != null && this.getIsQuery().equals("1")){
            return true;
        }
        return false;
    }

    public boolean isIncrementField() {
        if(this.getIsIncrement() != null && this.getIsIncrement().equals("1")){
            return true;
        }
        return false;
    }

    public boolean isListShow() {
        if(this.getIsListShow() != null && this.getIsListShow().equals("1")){
            return true;
        }
        return false;
    }

    public boolean isRequiredField() {
        if(this.getIsRequired() != null && this.getIsRequired().equals("1")){
            return true;
        }
        return false;
    }


}
