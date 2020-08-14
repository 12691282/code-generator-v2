<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/core/base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <link rel="stylesheet" href="/js/plus/bootstrap/css/bootstrap.min.css"  media="screen">
    <link rel="stylesheet" href="/js/plus/bootstrap/css/bootstrap-responsive.min.css" >
    <title>Code Generator Table</title>
</head>
<body>


<div class="container">

    <div class="masthead">
        <h3 class="muted">代码生成系统</h3>
        <div class="navbar">
            <div class="navbar-inner">
                <div class="container">
                    <ul class="nav">
                        <li class="active"><a href="#">生成代码详情</a></li>
                    </ul>
                </div>
            </div>
        </div><!-- /.navbar -->
    </div>
    <button class="btn btn-success" data-toggle="modal" onclick="javascript:window.location.href='list'" data-target="#databaseInfoModal">返回列表</button>
    <h3>数据库表 :  ${tableName} </h3>

    <ul class="nav nav-tabs nav-event-clazz">
        <li role="presentation" value="baseInfo" ><a href="#">基本信息</a>
        </li>
        <li role="presentation" value="filedInfo"  class="active" ><a href="#">字段信息</a>
        </li>
    </ul>
    <div class="content-event-clazz" id="baseInfo"  style="display: none">
        <br/>
        <form id="tableInfoEntityForm" method="post" >
            <input type="hidden" name="generatId" value="${infoEntity.generatId}">
            <div class="form-inline">
                <div class="form-group">
                    <label>表名称</label>
                    <input type="text" class="form-control"  placeholder="表名称" name="tableName" value="${infoEntity.tableName}">
                </div>
                <div class="form-group" style="margin-left: 20px;">
                    <label>表描述</label>
                    <input type="text" class="form-control"   placeholder="表描述" name="tableComment" value="${infoEntity.tableComment}">
                </div>
                <div class="form-group" style="margin-left: 20px;">
                    <label>实体类名称</label>
                    <input type="text" class="form-control"  placeholder="实体类名称" name="className" value="${infoEntity.className}" >
                </div>
            </div>
            <div class="form-inline" style="margin-top: 15px;">
                <div class="form-group">
                    <label>作者</label>
                    <input type="text" class="form-control" style="margin-left: 14px;" placeholder="作者" name="functionAuthor"
                           value="${infoEntity.functionAuthor}">
                </div>
                <div class="form-group" style="margin-left: 20px;">
                    <label>包路径</label>
                    <input type="text" class="form-control"   placeholder="包路径" name="packageName" value="${infoEntity.packageName}" >
                </div>
                <div class="form-group" style="margin-left: 20px;">
                    <label>模块名</label>
                    <input type="text" class="form-control"  placeholder="模块名" name="moduleName" value="${infoEntity.moduleName}" >
                </div>
            </div>
            <div class="form-inline" style="margin-top: 15px;">
                <div class="form-group">
                    <label>业务名</label>
                    <input type="text" class="form-control"  placeholder="业务名" name="businessName" value="${infoEntity.businessName}" >
                </div>
                <div class="form-group" style="margin-left: 20px;">
                    <label>功能名</label>
                    <input type="text" class="form-control"   placeholder="功能名" name="functionName" value="${infoEntity.functionName}" >
                </div>
            </div>
            <div class="form-inline" style="margin-top: 15px;">
                <div class="form-group">
                    <label>备注</label>
                    <textarea type="text" style="margin-left: 14px;width: 326px;height: 64px;" class="form-control" name="remark"
                              placeholder="备注" >${infoEntity.remark}</textarea>
                </div>
            </div>
            <br/>
            <button type="button" class="btn btn-default" id="confirmTableInfo" >确定</button>
        </form>
    </div>
    <div class="content-event-clazz" id="filedInfo" >
        <table class="table table-hover">
            <thead>
            <tr>
                <th>列名称</th>
                <th>列描述</th>
                <th>列类型</th>
                <th>JAVA字段类型</th>
                <th>JAVA字段名</th>
                <th>是否主键</th>
                <th>是否自增</th>
                <th>是否列表展示字段</th>
                <th>是否必填</th>
                <th>是否编辑</th>
                <th>是否查询字段</th>
                <th>查询方式</th>
                <th>显示类型</th>
                <th>排序</th>
                <th align="right">操作</th>
            </tr>
            </thead>
            <tbody >
            <c:forEach items="${columnList}" var="v"  varStatus="status">
                <tr  class="column-event-clazz" >
                    <td >${v.columnName}</td>
                    <td ><input class="column-value-clazz" type="text" name="columnComment" value="${v.columnComment}" style="width:120px;"></td>
                    <td >${v.columnType}</td>
                    <td >
                        <select class="column-value-clazz" name="javaType"  style="width:80px;">
                            <c:forEach items="${javaTypeList}" var="jType" >
                                <option value="${jType}" <c:if test="${jType == v.javaType}">selected</c:if>>${jType}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td ><input class="column-value-clazz" type="text" name="javaField" value="${v.javaField}"  style="width:120px;"></td>
                    <td ><input class="column-value-clazz" type="checkbox"  name="isPk" <c:if test="${v.isPk == 1}">checked</c:if>></td>
                    <td ><input class="column-value-clazz" type="checkbox"  name="isIncrement" <c:if test="${v.isIncrement == 1}">checked</c:if>></td>
                    <td ><input class="column-value-clazz" type="checkbox"  name="isListShow" value="${v.isListShow}" <c:if test="${v.isListShow == 1}">checked</c:if>></td>
                    <td ><input class="column-value-clazz" type="checkbox"  name="isRequired" value="${v.isRequired}" <c:if test="${v.isRequired == 1}">checked</c:if>></td>
                    <td ><input class="column-value-clazz" type="checkbox"  name="isEdit" <c:if test="${v.isEdit == 1}">checked</c:if>></td>
                    <td ><input class="column-value-clazz" type="checkbox"  name="isQuery" <c:if test="${v.isQuery == 1}">checked</c:if>></td>
                    <td >
                        <select class="column-value-clazz" name="queryType"  style="width:90px;">
                            <c:forEach items="${queryMethodList}" var="queryMethod" >
                                <option value="${queryMethod}" <c:if test="${queryMethod == v.queryType}">selected</c:if>>${queryMethod}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td >
                        <select class="column-value-clazz" name="htmlType"  style="width:90px;">
                            <c:forEach items="${htmlInputTypeList}" var="htmlInputType" >
                                <option value="${htmlInputType}" <c:if test="${htmlInputType == v.htmlType}">selected</c:if>>${htmlInputType}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td >${v.sort}</td>
                    <td><button class="btn btn-success" type="button" value="${v.columnId}">保存</button></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <div class="footer">
        <p>&copy; lion spring boot gamma - v2 2020</p>
    </div>

</div> <!-- /container -->
<!-- js 加载 -->
<script type="text/javascript" src="/js/plus/jquery/jquery-1.12.4.min.js"></script>
<script type="text/javascript" src="/js/plus/bootstrap/js/bootstrap.min.js"></script>
<!-- 本页面js -->
<script type="text/javascript" src="/js/page/generator/tableInfoDetail.js"></script>
</body>
</html>