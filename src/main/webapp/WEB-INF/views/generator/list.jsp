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
			<button class="btn btn-default" data-toggle="modal" data-target="#configInfoModal" >配置信息</button>
			<button class="btn btn-info" id="generatorToModels" >生成代码</button>
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
              
 			<form id="configForm" method="post">
				<input type="hidden"  name="isBaseModel" value="" />
              <tbody>
              <c:forEach items="${list}" var="v"  varStatus="status">
	               <tr >
	               	   <td>
						  <input type="checkbox" name="tableArr" value="${v.generatId}"> ${status.index + 1}
	               	   </td>
	                   <td >${v.tableName}</td>
					   <td >${v.tableComment}</td>
					   <td >${v.createTime}</td>
	                  <td><button class="btn btn-success" type="button" value="${v.tableName}">查看</button></td>
					  <td><button class="btn btn-info" type="button">生成代码</button></td>
	               </tr>
              </c:forEach>
              </tbody>
            </table>
      <hr>

      <div class="footer">
        <p>&copy; lion spring boot gamma - v2 2020</p>
      </div>

    </div> <!-- /container -->


	<div class="modal fade" tabindex="-1" id="configInfoModal" role="dialog">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">信息配置</h4>
				</div>
				<div class="modal-body">

						<div class="control-group">
							<label class="control-label" for="inputEmail">base Model 路径</label>
							<div class="controls">
								<input type="text" name="baseModelPath" style="width: 450px" value="${configInfo.baseModelPath}">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="inputEmail">基础模块名称</label>
							<div class="controls">
								<input type="text" name="baseModelName" style="width: 450px" value="${configInfo.baseModelName}">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="inputEmail">数据层后缀名称(Dao 或 Mapper 或其他)</label>
							<div class="controls">
								<input type="text" name="daoNameSuffix" style="width: 450px" value="${configInfo.daoNameSuffix}">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="inputEmail">返回类全路径</label>
							<div class="controls">
								<input type="text" name="returnClassPath" style="width: 450px" value="com.delta.common.utils.SystemHttpResponse">
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" data-dismiss="modal">确定</button>
				</div>
				</form>
			</div><!-- /.modal-content -->
		</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->

	<div class="modal fade" tabindex="-1" id="databaseInfoModal" role="dialog">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title">数据库配置</h4>
				</div>
				<div class="modal-body">
					<form  id="connectDataBaseForm"  style="margin-left: 80px;" method="post">
						<div class="control-group">
							<label class="control-label" for="inputEmail" style="margin-left: -80px;">数据库地址</label>
							<div class="controls">
								<input type="text" name="url" style="width: 580px; margin-left: -80px;" value="${dataBaseUrl}">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" style="margin-left: -80px;" for="inputEmail">数据库驱动名</label>
							<div class="controls">
								<input type="text" style="margin-left: -80px;" name="driver" value="${dataBaseDriver}">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" style="margin-left: -80px;" for="inputEmail">用户名</label>
							<div class="controls">
								<input type="text" style="margin-left: -80px;" name="username" value="${dataBaseUsername}">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" style="margin-left: -80px;" for="inputEmail">密码</label>
							<div class="controls">
								<input type="text" style="margin-left: -80px;"  name="password" value="${dataBasePassword}">
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="confirmDatabaseInfo">确定</button>
				</div>
			</div><!-- /.modal-content -->
		</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->

    
<!-- js 加载 -->
 <script type="text/javascript" src="/js/plus/jquery/jquery-1.12.4.min.js"></script>
 <script type="text/javascript" src="/js/plus/bootstrap/js/bootstrap.min.js"></script>
 <!-- 本页面js -->
 <script type="text/javascript" src="/js/page/generator/list.js"></script>

</body>
</html>