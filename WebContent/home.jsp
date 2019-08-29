<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Home</title>
<!-- Bootstrap -->
<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet"
	media="screen">
<link href="bootstrap/css/bootstrap-responsive.min.css" rel="stylesheet"
	media="screen">
<link href="assets/styles.css" rel="stylesheet" media="screen">
<link href="assets/DT_bootstrap.css" rel="stylesheet" media="screen">
<!--[if lte IE 8]><script language="javascript" type="text/javascript" src="vendors/flot/excanvas.min.js"></script><![endif]-->
<!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
            <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
        <![endif]-->
<script src="vendors/modernizr-2.6.2-respond-1.1.0.min.js"></script>
</head>

<body>
	<div class="navbar navbar-fixed-top">
		<div class="navbar-inner">
			<div class="container-fluid">
				<a class="btn btn-navbar" data-toggle="collapse"
					data-target=".nav-collapse"> <span class="icon-bar"></span> <span
					class="icon-bar"></span> <span class="icon-bar"></span>
				</a> <a class="brand" href="#">白熊云</a>
				<div class="nav-collapse collapse">
					<ul class="nav pull-right">
						<li class="dropdown"><a href="#" role="button"
							class="dropdown-toggle" data-toggle="dropdown"> <i
								class="icon-user"></i> ${name} <i class="caret"></i>
						</a>
							<ul class="dropdown-menu">
								<li><a tabindex="-1" href="LogoutServlet">退出</a></li>
								<li class="divider"></li>
								<li><a tabindex="-1" href="login.html">注销</a></li>
							</ul></li>
					</ul>
					<ul class="nav">
						<li class="active"><a href="#">网盘</a></li>
						<li class="dropdown"><a href="#" data-toggle="dropdown"
							class="dropdown-toggle">分享<b class="caret"></b>
						</a>
							<ul class="dropdown-menu" id="menu1">
								<li><a href="#">Tools <i class="icon-arrow-right"></i>
								</a>
									<ul class="dropdown-menu sub-menu">
										<li><a href="#">Reports</a></li>
										<li><a href="#">Logs</a></li>
										<li><a href="#">Errors</a></li>
									</ul></li>
								<li><a href="#">SEO Settings</a></li>
								<li><a href="#">Other Link</a></li>
								<li class="divider"></li>
								<li><a href="#">Other Link</a></li>
								<li><a href="#">Other Link</a></li>
							</ul></li>
						<li class="dropdown"><a href="#" role="button"
							class="dropdown-toggle" data-toggle="dropdown">组<i
								class="caret"></i>
						</a>
							<ul class="dropdown-menu">
								<li><a tabindex="-1" href="#">Blog</a></li>
								<li><a tabindex="-1" href="#">News</a></li>
								<li><a tabindex="-1" href="#">Custom Pages</a></li>
								<li><a tabindex="-1" href="#">Calendar</a></li>
								<li class="divider"></li>
								<li><a tabindex="-1" href="#">FAQ</a></li>
							</ul></li>
						<li class="dropdown"><a href="#" role="button"
							class="dropdown-toggle" data-toggle="dropdown">Users <i
								class="caret"></i>
						</a>
							<ul class="dropdown-menu">
								<li><a tabindex="-1" href="#">User List</a></li>
								<li><a tabindex="-1" href="#">Search</a></li>
								<li><a tabindex="-1" href="#">Permissions</a></li>
							</ul></li>
					</ul>
				</div>
				<!--/.nav-collapse -->
			</div>
		</div>
	</div>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span3" id="sidebar">
				<ul class="nav nav-list bs-docs-sidenav nav-collapse collapse">
					<li class="active"><a href="FileListServlet"><i
							class="icon-chevron-right"></i>全部文件</a></li>
					<li><a href="FileListServlet?filter=picture"><i
							class="icon-chevron-right"></i>图片</a></li>
					<li><a href="FileListServlet?filter=music"><i
							class="icon-chevron-right"></i>音频</a></li>
					<li><a href="FileListServlet?filter=video"><i
							class="icon-chevron-right"></i> 视频</a></li>
					<li><a href="FileListServlet?filter=document"><i
							class="icon-chevron-right"></i>文档</a></li>
					<li><a href="FileListServlet?filter=zip"><i
							class="icon-chevron-right"></i>压缩文件</a></li>
					<li><a href="FileListServlet?filter=torrent"><i
							class="icon-chevron-right"></i>种子</a></li>
				</ul>
			</div>
			<!--/span-->
			<div class="span9" id="content">
				<div class="row-fluid">
					<!-- block -->
					<div class="block">
						<div class="navbar navbar-inner block-header">
							<div class="muted pull-left">我的网盘</div>
						</div>
						<div class="block-content collapse in">
							<div class="span12">
								<div class="table-toolbar">
									<div class="btn-group">
										<button class="btn btn-success"  data-toggle="modal" data-target="#uploadModel">
												上传<i class="icon-plus icon-white"></i>
											</button>
									</div>
									<!-- 上传模态框  -->
									<div class="modal fade" id="uploadModel">
										<div class="modal-dialog">
											<div class="modal-content">
												<div class="modal-header">
													<h4 class="modal-title" id="myModalLabel">上传文件</h4>
													<form method="post" action="UploadServlet"
														enctype="multipart/form-data">
														<input type="file" name="file" /> 
														<input type="submit" value="上传" />
													</form>
												</div>
												<div class="modal-body"></div>
											</div>
										</div>
									</div>
									<!-- 排序  -->
									<div class="btn-group pull-right">
										<form action="FileListServlet" method="get" id="sortingways">
											<select name="sortingways" onchange="on_choice()">
												<option value="time" ${sortingways=="time"?'selected':''}>上传日期</option>
												<option value="name" ${sortingways=="name"?'selected':''}>名称</option>
												<option value="size" ${sortingways=="size"?'selected':''}>文件大小</option>
											</select>
										</form>
									</div>
								</div>
									<div id="example2_wrapper" class="dataTables_wrapper form-inline" role="grid">
									<div class="row">
										<div class="span6">
											<div id="example2_length" class="dataTables_length">
												<label> 
												</label>
											</div>
										</div>
										<div class="span6 pull-right">
											<div id="example2_length" class="dataTables_length">
												<label>
													<form action="SearchFileServlet" enctype="multipart/form-data" method="get">
														<input type="text" name="searchthing"> 
														<button class="btn btn-primary" type="submit">搜索</button>
													</form>
												</label>
											</div>
										</div>
									</div>
								
								<table cellpadding="0" cellspacing="0" border="0" class="table table-striped table-bordered" id="filestable">
									<thead>
										<tr>
											<th>文件名</th>
											<th>大小</th>
											<th>上传时间</th>
											<th>操作</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="files" items="${files}">
											<tr>
												<th>${files.getFileName()}</th>
												<th>${files.getSize()}字节</th>
												<th>${files.getUpdateTime()}</th>
												<th><c:url value="DownloadServlet" var="downurl">
														<c:param name="filename" value="${files.getFileName()}"></c:param>
													</c:url> <a href="${downurl}">下载</a> <c:url value="DeleteServlet"
														var="deleteurl">
														<c:param name="filename" value="${files.getFileName()}"></c:param>
													</c:url> <a href="${deleteurl}">删除</a></th>
											</tr>
										</c:forEach>
									</tbody>
								</table>
								</div>
							</div>
						</div>
					</div>
					<!-- /block -->
				</div>
			</div>
		</div>
		<hr>
	</div>
	<!--/.fluid-container-->

	<script src="vendors/jquery-1.9.1.js"></script>
	<script src="bootstrap/js/bootstrap.min.js"></script>
	<script src="vendors/datatables/js/jquery.dataTables.min.js"></script>
	<script src="assets/scripts.js"></script>
	<script src="assets/DT_bootstrap.js"></script>
	<script>
		function on_choice() {
			var form = document.getElementById('sortingways');
			form.submit();
		}
	</script>
</body>
</html>