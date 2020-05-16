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
        <h3 class="muted">代码生成系统 - 前后分离版本-v2</h3>
        <div class="navbar">
          <div class="navbar-inner">
            <div class="container">
              <ul class="nav">
                <li class="active"><a href="#">数据表</a></li>
             <!--    <li><a href="#">Projects</a></li>
                <li><a href="#">Services</a></li>
                <li><a href="#">Downloads</a></li>
                <li><a href="#">About</a></li>
                <li><a href="#">Contact</a></li> -->
              </ul>
            </div>
          </div>
        </div><!-- /.navbar -->
      </div>
		<div>
			<button class="btn btn-default" data-toggle="modal" data-target="#databaseInfoModal">连接数据库</button>
			<button class="btn btn-default" data-toggle="modal" data-target="#configInfoModal" >配置信息</button>
			<button class="btn btn-info" id="generatorToModels" >生成模块</button>
		</div>
 			 <table class="table table-hover">
              <thead>
                <tr>
                  <th>#</th>
                  <th style="width: 800px;">表名</th>
                  <th align="right">操作</th>
                </tr>
              </thead>
              
 			<form id="configForm" method="post">             
              <tbody>
              <c:forEach items="${list}" var="v"  varStatus="status">
	               <tr >
	               	  <td>
						  <input type="checkbox" name="tableArr" value="${v}"> ${status.index + 1}
	               	   </td>
	                  <td >${v}</td>
	                  <td><button class="btn btn-success" type="button">查看</button></td>
	               </tr>
              </c:forEach>
              </tbody>
            </table>

      <hr>

      <div class="footer">
        <p>&copy; lion spring boot gamma - v2 2020</p>
      </div>

    </div> <!-- /container -->
    
    <!-- 模态框（Modal） -->
<div class="modal fade"   id="configInfoModal" tabindex="-1" role="dialog"  aria-hidden="true">
    <div class="modal-dialog"  >
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">信息配置</h4>
            </div>
            <div class="form-horizontal" style="height: 330px;padding: 15px;">
            <div class="control-group">
			    <label class="control-label" for="inputEmail">是否生成base Bean</label>
			    <div class="controls">
			      <input type="checkbox" id="isBaseBean" name="isBaseBean" value=false />
			    </div>
			  </div>
            
            <div class="control-group">
			    <label class="control-label" for="inputEmail">是否生成base Model</label>
			    <div class="controls">
			      <input type="checkbox" id="isBaseModel"  name="isBaseModel" value=false />
			    </div>
			  </div>

			<div class="control-group"><!--模块名称-->
				<label class="control-label" for="inputEmail">基础模块名称</label>
				<div class="controls">
					<input type="text" name="baseModelName" value="">
				</div>
			</div>


            <div class="control-group"><!--基础路径-->
			    <label class="control-label" for="inputEmail">base_package</label>
			    <div class="controls">
			      <input type="text" name="basePackage" value="com.delta">
			    </div>
			  </div>
            
			<div class="control-group">
				<label class="control-label" for="inputEmail">数据层后缀名称(Dao 或 Mapper 或其他)</label>
				<div class="controls">
					<input type="text" name="daoNameSuffix" value="dao">
				</div>
			 </div>


                <div class="control-group"><!--返回类全路径-->
                    <label class="control-label" for="inputEmail">返回类全路径</label>
                    <div class="controls">
                        <input type="text" name="returnClassPath" value="com.delta.common.utils.SystemHttpResponse">
                    </div>
                </div>

			  <div class="control-group">
			  
				  <label class="control-label" for="inputEmail">
				     <div class="dropdown">
						<button type="button" class="btn dropdown-toggle" id="dropdownMenu1" 
								data-toggle="dropdown">
							   页面样式
							<span class="caret"></span>
						</button>
						<ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">
							<li role="presentation">
								<a role="menuitem" tabindex="1" onmouseup="setToPageCss('elementUI')" href="#">bootstrap</a>
							</li>
							<li role="presentation">
								<a role="menuitem" tabindex="-1" onmouseup="setToPageCss('iviewUI')"  href="#">暂缺</a>
							</li>
						</ul>
					 </div>
				   </label>
				    <div class="controls">
				      <input type="text" name="pageCss" value="bootstrap">
				    </div>
			 </div>
            
			</div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
    <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" data-dismiss="modal">确定</button>
    </div>
 </form>   
</div>


   
     <!-- 模态框（Modal） -->
<div class="modal fade" style="width: 800px;" id="databaseInfoModal" tabindex="-1" role="dialog"  aria-hidden="true">

    <div class="modal-dialog"  >
        <div class="modal-content" >
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">数据库配置</h4>
            </div>
            <div class="modal-body form-horizontal">
            
            <div class="control-group">
			    <label class="control-label" for="inputEmail" style="margin-left: -80px;">数据库地址</label>
			    <div class="controls">
			      <input type="text" id="databaseUrl" style="width: 600px; margin-left: -80px;" value="${dbBean.url}">
			    </div>
			  </div>
            
              <div class="control-group">
			    <label class="control-label" style="margin-left: -80px;" for="inputEmail">用户名</label>
			    <div class="controls">
			      <input type="text" style="margin-left: -80px;" id="databaseUsername" value="${dbBean.username}">
			    </div>
			  </div>
			  
			  <div class="control-group">
			    <label class="control-label" style="margin-left: -80px;" for="inputEmail">密码</label>
			    <div class="controls">
			      <input type="text"  style="margin-left: -80px;" id="databasePassword" value="${dbBean.password}">
			    </div>
			  </div>
			</div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
    <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="confirmDatabaseInfo">确定</button>
    </div>
</div>

    
<!-- js 加载 -->
 <script type="text/javascript" src="/js/plus/jquery/jquery-1.12.4.min.js"></script>
 <script type="text/javascript" src="/js/plus/bootstrap/js/bootstrap.min.js"></script>
 <!-- 本页面js -->
 <script type="text/javascript" src="/js/page/table/list.js"></script>

</body>
</html>