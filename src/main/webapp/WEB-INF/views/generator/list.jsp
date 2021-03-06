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
	<!-- 隐藏参数 -->
  <input type="hidden" id="isBaseModelVal"  value="${configInfo.isBaseModel}">

  <div class="container">

      <div class="masthead">
        <h3 class="muted">代码生成系统 - use velocity version-2</h3>
        <div class="navbar">
          <div class="navbar-inner">
            <div class="container">
              <ul class="nav">
                <li class="active"><a href="#">数据表</a></li>
              </ul>
            </div>
          </div>
        </div><!-- /.navbar -->
      </div>
		<div>
			<button class="btn btn-default" data-toggle="modal" data-target="#databaseInfoModal">导入目标数据</button>
			<button class="btn btn-info" data-toggle="modal" data-target="#parameterConfigModal">参数配置</button>
			<button class="btn btn-info" id="toGeneratorCode" >生成代码</button>
		</div>
 			 <table class="table table-hover">
              <thead>
                <tr>
					<th>#</th>
                    <th style="width: 300px;">表名</th>
					<th style="width: 300px;">表描述</th>
					<th style="width: 300px;">创建时间</th>
                    <th align="right">操作</th>
                </tr>
              </thead>
              <tbody>
              <c:forEach items="${list}" var="v"  varStatus="status">
	               <tr >
	               	   <td><input type="checkbox" name="tableArr" value="${v.tableName}">${status.index + 1}</td>
	                   <td >${v.tableName}</td>
					   <td >${v.tableComment}</td>
					   <td >${v.createTime}</td>
                       <td><button class="btn btn-danger" type="button" value="${v.generatId}">删除</button></td>
	                  <td><button class="btn btn-success" type="button" value="${v.tableName}">查看</button></td>
					  <td><button class="btn btn-info" type="button" value="${v.tableName}" >生成代码</button></td>
	               </tr>
              </c:forEach>
              </tbody>
            </table>
      <hr>

      <div class="footer">
        <p>&copy; lion spring boot gamma - v2 2020</p>
      </div>

    </div> <!-- /container -->



    <div class="modal fade" tabindex="-1" id="databaseInfoModal" role="dialog"> <!-- modal 开始 -->
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title">数据库配置</h4>
                </div>
                <div class="modal-body">
                    <form  method="post" action="connectTargetDataBase">
                        <div class="control-group">
                            <label class="control-label" >数据库地址</label>
                            <div class="controls">
                                <textarea name="url" style="width: 580px;height: 70px;">${dataBaseUrl}</textarea>
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label" >数据库驱动名</label>
                            <div class="controls">
                                <input type="text"  name="driver" value="${dataBaseDriver}">
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label" >用户名</label>
                            <div class="controls">
                                <input type="text"  name="username" value="${dataBaseUsername}">
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label"  >密码</label>
                            <div class="controls">
                                <input type="text" name="password" value="${dataBasePassword}">
                            </div>
                        </div>

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="submit" class="btn btn-primary" >确定</button>
                </div>
                </form>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- modal 结束 -->



    <div class="modal fade" tabindex="-1" id="parameterConfigModal" role="dialog"> <!-- modal 开始 -->
        <div class="modal-dialog" role="document">
            <div class="modal-content" style="width: 400px;">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title">参数配置</h4>
                </div>
                <form id="confirmConfigForm">
                <input type="hidden"  name="id" value="${config.id}">
                <div class="modal-body">
                        <div class="control-group">
                            <label class="control-label" >包路径</label>
                            <div class="controls">
                                <input type="text"  name="basePackage" value="${config.basePackage}">
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label" >模块名</label>
                            <div class="controls">
                                <input type="text"  name="baseModelName" value="${config.baseModelName}">
                            </div>
                        </div>

                        <div class="control-group">
                           <label class="control-label" >数据库类后缀</label>
                           <div class="controls">
                               <input type="text"  name="dataBaseSuffix" value="${config.dataBaseSuffix}">
                           </div>
                        </div>

                        <div class="control-group">
                            <label class="control-label" >原型类后缀</label>
                            <div class="controls">
                                <input type="text"  name="prototypeClassSuffix" value="${config.prototypeClassSuffix}">
                            </div>
                        </div>

                        <div class="control-group">
                            <label class="control-label" >作者名</label>
                            <div class="controls">
                                <input type="text"  name="authorName" value="${config.authorName}">
                            </div>
                        </div>
                        <div>请用符号 , 分割数据</div>
                        <div class="control-group">
                            <label class="control-label">不显示字段</label>
                            <div class="controls">
                                <textarea name="noShowFiled" style="width: 380px;height: 70px;">${config.noShowFiled}</textarea>
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label">java类型</label>
                            <div class="controls">
                                <textarea name="javaTypeConfig" style="width: 380px;height: 70px;">${config.javaTypeConfig}</textarea>
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label">查询方法</label>
                            <div class="controls">
                                <textarea name="queryMethodConfig" style="width: 380px;height: 70px;">${config.queryMethodConfig}</textarea>
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label">页面输入类型</label>
                            <div class="controls">
                                <textarea name="htmlInputTypeConfig" style="width: 380px;height: 70px;">${config.htmlInputTypeConfig}</textarea>
                            </div>
                        </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" class="btn btn-primary" id="confirmConfigButto" >确定</button>
                </div>
                </form>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- modal 结束 -->


    <!-- js 加载 -->
 <script type="text/javascript" src="/js/plus/jquery/jquery-1.12.4.min.js"></script>
 <script type="text/javascript" src="/js/plus/bootstrap/js/bootstrap.min.js"></script>
 <!-- 本页面js -->
 <script type="text/javascript" src="/js/page/generator/list.js"></script>

</body>
</html>