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
        <h3 class="muted">代码生成系统 - 前后分离版本-v2</h3>
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
              

              <tbody>

              </tbody>
            </table>

      <hr>

      <div class="footer">
        <p>&copy; lion spring boot gamma - v2 2020</p>
      </div>

    </div> <!-- /container -->



    
<!-- js 加载 -->
 <script type="text/javascript" src="/js/plus/jquery/jquery-1.12.4.min.js"></script>
 <script type="text/javascript" src="/js/plus/bootstrap/js/bootstrap.min.js"></script>
 <!-- 本页面js -->
 <script type="text/javascript" src="/js/page/table/list.js"></script>

</body>
</html>