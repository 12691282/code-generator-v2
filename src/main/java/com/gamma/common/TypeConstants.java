package com.gamma.common;

import com.google.common.collect.Lists;

import java.util.List;

public class TypeConstants {

    /** 数据库字符串类型 */
    public static final List<String> COLUMN_TYPE_STR = Lists.newArrayList("char", "varchar", "narchar", "varchar2", "tinytext", "text",
            "mediumtext", "longtext" );

    /** 数据库时间类型 */
    public static final List<String> COLUMN_TYPE_TIME = Lists.newArrayList( "datetime", "time", "date", "timestamp" );

    /** 数据库数字类型 */
    public static final List<String> COLUMN_TYPE_NUMBER = Lists.newArrayList( "tinyint", "smallint", "mediumint", "int", "number", "integer",
            "bigint", "float", "float", "double", "decimal" );


    /** 字符串类型 */
    public static final String JAVA_FILED_TYPE_STRING = "String";

    /** 整型 */
    public static final String JAVA_FILED_TYPE_INTEGER = "Integer";

    /** 长整型 */
    public static final String JAVA_FILED_TYPE_LONG = "Long";

    /** 高精度计算类型 */
    public static final String JAVA_FILED_TYPE_BIGDECIMAL = "BigDecimal";

    /** 时间类型 */
    public static final String JAVA_FILED_TYPE_DATE = "Date";

    /** 需要 */
    public static final String REQUIRE = "1";

}
