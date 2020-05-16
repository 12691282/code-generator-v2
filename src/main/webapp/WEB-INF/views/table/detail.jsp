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
                <li class="active"><a href="#">数据表</a></li>
              </ul>
            </div>
          </div>
        </div><!-- /.navbar -->
      </div>
      <button class="btn btn-success" data-toggle="modal" onclick="javascript:window.location.href='list'" data-target="#databaseInfoModal">返回列表</button>
			<h3>${tableName} 结构</h3>
 			 <table class="table table-hover">
              <thead>
                <tr>
                  <th>#</th>
                  <th>字段名</th>
                  <th>字段类型</th>
                  <th>备注</th>
                  <th>默认值</th>
                  <th>允许为空</th>
                </tr>
              </thead>
              <tbody>
              <c:forEach items="${list}" var="obj"  varStatus="status">
	               <tr >
	               	  <td>${status.index + 1}</td>
	                  <td>${obj.filed}</td>
	                  <td>${obj.type}</td>
	                  <td>${obj.comment}</td>
	                  <td>${obj.def}</td>
	                  <td>${obj.isNull}</td>
	               </tr>
              </c:forEach>
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

</body>
</html>