package com.gamma.service.entity;

import com.gamma.annotation.Column;
import com.gamma.annotation.PrimaryKey;
import com.gamma.annotation.Table;
import com.gamma.common.TypeConstants;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    /**
     * 导入类列表
     */
    Set<String> importList;

    /** 主键类 */
    private GeneratorTableColumnEntity pkColumn;

    /** 字段列表 */
    private List<GeneratorTableColumnEntity> columnList;

    public String getPackagePrefix() {
        int lastIndex = packageName.lastIndexOf(".");
        String basePackage = StringUtils.substring(packageName, 0, lastIndex);
        return basePackage;
    }

    /**
     * 设置主键列信息
     */
    public void setPkColumn()
    {
        for (GeneratorTableColumnEntity column : this.columnList)
        {
            if (column.isPrimaryKey())
            {
                this.setPkColumn(column);
                break;
            }
        }
        if (this.getPkColumn() == null)
        {
            this.setPkColumn(this.getColumnList().get(0));
        }
    }


    public void setImportList(List<String> noShowFiledList) {
        this.importList = new HashSet();
        for (GeneratorTableColumnEntity column : this.columnList)
        {
            column.setNoShowList(noShowFiledList);
            Boolean isNoShow = column.isSuperColumn();

            if (isNoShow && TypeConstants.JAVA_FILED_TYPE_DATE.equals(column.getJavaType()))
            {
                importList.add("java.util.Date");
            }
            else if (!isNoShow && TypeConstants.JAVA_FILED_TYPE_BIGDECIMAL.equals(column.getJavaType()))
            {
                importList.add("java.math.BigDecimal");
            }
        }

    }
}
