DROP TABLE IF EXISTS `generator_table_info`;
CREATE TABLE `generator_table_info`  (
  `generat_id` varchar(64) NOT NULL COMMENT '编号',
  `table_name` varchar(200)  COMMENT '表名称',
  `table_comment` varchar(500)  COMMENT '表描述',
  `class_name` varchar(100)  COMMENT '实体类名称',
  `package_name` varchar(100)  COMMENT '生成包路径',
  `module_name` varchar(30)  COMMENT '生成模块名',
  `business_name` varchar(30)  COMMENT '生成业务名',
  `function_name` varchar(50)  COMMENT '生成功能名',
  `function_author` varchar(50)  COMMENT '生成功能作者',
  `options` varchar(1000)  COMMENT '其它生成选项',
  `remark` varchar(500)  COMMENT '备注',
  `create_time` varchar(30)   COMMENT '创建时间',
  PRIMARY KEY (`generat_id`)
);

DROP TABLE IF EXISTS `generator_table_column`;
CREATE TABLE `generator_table_column`  (
  `column_id` varchar(64) NOT NULL COMMENT '编号',
  `table_id` varchar(64)  COMMENT '归属表编号',
  `column_name` varchar(50)  COMMENT '列名称',
  `column_comment` varchar(500)  COMMENT '列描述',
  `column_type` varchar(100)  COMMENT '列类型',
  `java_type` varchar(500)  COMMENT 'JAVA类型',
  `java_field` varchar(200)  COMMENT 'JAVA字段名',
  `is_pk` char(1)  COMMENT '是否主键（1是）',
  `is_increment` char(1)  COMMENT '是否自增（1是）',
  `is_list_show` char(1)  COMMENT '是否列表展示字段（1是）',
  `is_required` char(1)  COMMENT '是否必填（1是）',
  `is_edit` char(1)  COMMENT '是否编辑字段（1是）',
  `is_query` char(1)  COMMENT '是否查询字段（1是）',
  `query_type` varchar(200) COMMENT '查询方式（等于、不等于、大于、小于、范围）',
  `html_type` varchar(200)  COMMENT '显示类型（文本框、文本域、下拉框、复选框、单选框、日期控件）',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`column_id`)
);
