<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getLocalPort()+path+"/";
%>
<!doctype html>
<html>
<head>
	<base href="<%=basePath%>">
	<meta charset="utf-8" />
	<title></title>
	<meta name="description" content="overview & stats"/>
	<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
	<link href="static/css/bootstrap.min.css" rel="stylesheet" />
	<link href="static/css/bootstrap-responsive.min.css" rel="stylesheet"/>
	<link rel="stylesheet" href="static/css/font-awesome.min.css"/>
	<link rel="stylesheet" href="static/css/chosen.css" />
	<link rel="stylesheet" href="static/css/ace.min.css" />
	<link rel="stylesheet" href="static/css/ace-responsive.min.css"/>
	<link rel="stylesheet" href="static/css/ace-skins.min.css" />
	<link rel="stylesheet" href="static/css/jquery.uploader.css">
	<link rel="stylesheet" href="static/css/style.css" media="screen" type="text/css">
</head>

<body>
	<div id="zhongxin">
		<table id="table_report" class="table table-striped table-bordered table-hover">
			<c:if test="${!empty pd}">
				<c:forEach items="${pd}" var="pdChild">
					<tr id="trTitle${pdChild.ID}">
						<td>
							附件名称：${pdChild.NAME}
							<div style="float: right;" align="right">
								<a href="javascript:" class="btn-small" onclick="delete_attach_file(this, '${pdChild.ID}')">删除</a>
							</div>
						</td>
					</tr>
					<tr id="trBody${pdChild.ID}">
						<td>
							<ul id="IMGLIST">
								<c:forEach items="${fn:split(pdChild.FILE_PATH, ',')}" var="filepath" varStatus="varIndex">
									<li><img src="<%=basePath%>uploadFiles/file/images/${filepath}" /></li>
								</c:forEach>
							</ul>
						</td>
					</tr>
				</c:forEach>
			</c:if>
			<c:if test="${empty pd}">
				<tr><td style="text-align: center;">该融资标没有附件!</td></tr>
			</c:if>
			<tr>
				<td style="text-align: center;" colspan="10">
					<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">退出</a>
				</td>
			</tr>
		</table>
	</div>

	<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>

<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
<script src="static/js/bootstrap.min.js"></script>
<script src="static/js/ace-elements.min.js"></script>
<script src="static/js/ace.min.js"></script>
<script type="text/javascript" src="static/js/jquery.tips.js"></script>
<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script><!-- 下拉框 -->
<script type="text/javascript" src="static/js/bootstrap-datepicker.min.js"></script><!-- 日期框 -->
<script type="text/javascript" src="static/js/myjs/validate.js"></script>
<!--script type="text/javascript" src="../static/js/jquery/jquery-1.7.2.min.js"></script-->
<script type="text/javascript" src="<%=basePath%>static/js/jquery.uploader.js"></script>
<script type="text/javascript" src="static/js/bootbox.min.js"></script><!-- 确认窗口 -->

<script type="text/javascript">
	function delete_attach_file(obj, ID) {
		bootbox.confirm("确定要删除该附件吗?", function(result) {
			if(result) {
				$.post("${pageContext.request.contextPath}/projectinfo/deleteAttach", {"time":new Date(), "ID":ID}, function(data) {
					if(data == "success") {
						$("#trTitle"+ID).remove();
						$("#trBody"+ID).remove();
						if($('table tr').length == 1) {
							$("table").prepend("<tr><td style=\"text-align: center;\">该融资标没有附件!</td></tr>");
						}

					}
				})
			}
		});

	}

	function log(obj1, obj2){
		if ('console' in window) {
			obj2 ? console.log(obj1, obj2) : console.log(obj1);
		}
	}
	$(function(){
		$(top.hangge());

	});
</script>



</body>
</html>
