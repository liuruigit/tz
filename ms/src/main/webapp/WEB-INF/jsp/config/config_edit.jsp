<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getLocalPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<base href="<%=basePath%>">
		<meta charset="utf-8" />
		<title></title>
		<meta name="description" content="overview & stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<link href="static/css/bootstrap.min.css" rel="stylesheet" />
		<link href="static/css/bootstrap-responsive.min.css" rel="stylesheet" />
		<link rel="stylesheet" href="static/css/font-awesome.min.css" />
		<!-- 下拉框 -->
		<link rel="stylesheet" href="static/css/chosen.css" />
		
		<link rel="stylesheet" href="static/css/ace.min.css" />
		<link rel="stylesheet" href="static/css/ace-responsive.min.css" />
		<link rel="stylesheet" href="static/css/ace-skins.min.css" />
		
		<link rel="stylesheet" href="static/css/datepicker.css" /><!-- 日期框 -->
		<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
		<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		<script type="text/javascript" src="static/js/myjs/validate.js"></script>
<script type="text/javascript">
	
	
	//保存
	function save(){
		if(!check_blank("#KEY",'请输入配置KEY！'))return;
		if(!check_str_len("KEY的值在20个字符以内！","#KEY",20))return;
		if(!check_blank("#VALUE",'请输入配置VALUE！'))return;
		if(!check_str_len("VALUE的值在100个字符以内！","#VALUE",100))return;
		if(!check_blank("#DESR",'请输入配置描述！'))return;
		if(!check_str_len("配置描述在50个字符以内！","#DESR",50))return;
		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}
	
</script>
	</head>
<body>
	<form action="config/${msg }.do" name="Form" id="Form" method="post">
		<input type="hidden" name="ID" id="ID" value="${pd.ID}"/>
		<div id="zhongxin">
		<table id="table_report" class="table table-striped table-bordered table-hover">
			<tr>
				<td class="edit_td">配置KEY:</td>
				<td><input type="text" name="KEY" id="KEY" value="${pd.KEY}" maxlength="32" placeholder="这里输入配置KEY" title="配置KEY"/></td>
			</tr>
			<tr>
				<td class="edit_td">配置VALUE:</td>
				<td><input type="text" name="VALUE" id="VALUE" value="${pd.VALUE}" maxlength="100" placeholder="这里输入配置value" title="配置value"/></td>
			</tr>
			<tr>
				<td class="edit_td">类型:</td>
				<td>
					<select class="chzn-select" name="TYPE" id="TYPE" data-placeholder="类型" class="select_top">
						<option <c:if test="${pd.TYPE==1}">selected</c:if> value="1">系统配置</option>
						<option <c:if test="${pd.TYPE==4}">selected</c:if> value="4">业务配置</option>
						<option <c:if test="${pd.TYPE==3}">selected</c:if> value="3">短信配置</option>
						<option <c:if test="${pd.TYPE==2}">selected</c:if> value="2">域名配置</option>
						<option <c:if test="${pd.TYPE==5}">selected</c:if> value="5">辅助配置</option>
					</select>
				</td>
			</tr>
			<tr>
				<td class="edit_td">描述:</td>
				<td><input type="text" name="DESR" id="DESR" value="${pd.DESR}" maxlength="50" placeholder="描述" title="描述"/></td>
			</tr>
			<tr>
				<td style="text-align: center;" colspan="10">
					<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
					<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
				</td>
			</tr>
		</table>
		</div>
		
		<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>
		
	</form>
	
	
		<!-- 引入 -->
		<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
		<script src="static/js/bootstrap.min.js"></script>
		<script src="static/js/ace-elements.min.js"></script>
		<script src="static/js/ace.min.js"></script>
		<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script><!-- 下拉框 -->
		<script type="text/javascript" src="static/js/bootstrap-datepicker.min.js"></script><!-- 日期框 -->
		<script type="text/javascript">
		$(top.hangge());
		$(function() {
			
			//单选框
			$(".chzn-select").chosen(); 
			$(".chzn-select-deselect").chosen({allow_single_deselect:true}); 
			
			//日期框
			$('.date-picker').datepicker();
			
		});
		
		</script>
</body>
</html>