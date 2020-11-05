package com.gamma.service.entity;

import com.gamma.annotation.Column;
import com.gamma.annotation.PrimaryKey;
import com.gamma.annotation.Table;
import com.gamma.common.TypeConstants;
import com.gamma.service.mapper.TableColumnMapper;
import com.gamma.service.vo.GeneratorTableColumnVO;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Table("generator_table_info")
@Data
public class GeneratorTableInfoEntity {

    /**编号*/
    @Column("generat_id")
    @PrimaryKey
    private String generatId;

    /**表名称*/
    @Column("table_name")
    private String tableName;

    /**
     * 表描述
     */
    @Column("table_comment")
    private String tableComment;

    /**
     * 实体类名称
     */
    @Column("class_name")
    private String className;


    /**
     * 数据库类后缀
     */
    @Column("data_base_suffix")
    private String dataBaseSuffix;

    /**
     * 原型类后缀
     */
    @Column("prototype_class_suffix")
    private String prototypeClassSuffix;

    /**
     * 生成包路径
     */
    @Column("package_name")
    private String packageName;

    /**
     * 生成模块名
     */
    @Column("module_name")
    private String moduleName;

    /**生成业务名*/
    @Column("business_name")
    private String businessName;

    /**生成功能名*/
    @Column("function_name")
    private String functionName;

    /**生成功能作者*/
    @Column("function_author")
    private String functionAuthor;

    /**其它生成选项*/
    @Column("options")
    private String options;

    /**备注*/
    @Column("remark")
    private String remark;

    /**备注*/
    @Column("create_time")
    private String createTime;
    /**
     * 导入类列表
     */
    Set<String> importList;

    /** 主键类 */
    private GeneratorTableColumnVO pkColumn;

    /** 字段列表 */
    private List<GeneratorTableColumnVO> columnList;

    public String getPackagePrefix() {
        int lastIndex = packageName.lastIndexOf(".");
        String basePackage = StringUtils.substring(packageName, 0, lastIndex);
        return basePackage;
    }


    public void changeColumnToVo(List<GeneratorTableColumnEntity> columnEntityList) {
        this.importList = new HashSet();
        this.columnList = new ArrayList<>();
        for (GeneratorTableColumnEntity column : columnEntityList){
            GeneratorTableColumnVO columnVO = TableColumnMapper.INSTANCE.entityToVo(column);
            if (columnVO.isPrimaryKey()){
                this.setPkColumn(columnVO);
            }

            if (columnVO.isNoEdit()){
                if ( TypeConstants.JAVA_FILED_TYPE_DATE.equals(columnVO.getJavaType()))
                {
                    importList.add("java.util.Date");
                }
                else if (TypeConstants.JAVA_FILED_TYPE_BIGDECIMAL.equals(columnVO.getJavaType()))
                {
                    importList.add("java.math.BigDecimal");
                }
            }
            columnList.add(columnVO);
        }
        if (this.getPkColumn() == null)
        {
            this.setPkColumn(this.getColumnList().get(0));
        }
    }
}
