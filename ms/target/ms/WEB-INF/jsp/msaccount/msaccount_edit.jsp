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
		
<script type="text/javascript">
	
	
	//保存
	function save(){
		if(!check_blank("#ACC_NAME",'请输入用户名！'))return;
		if(!check_str_len("用户名在40个字符长度以内！","#ACC_NAME",40))return;
		if(!check_blank("#STATUS",'请输入账号状态！'))return;
		if(!check_blank("#MOBILE",'请输入电话！'))return;
		if(!check_telephone("#MOBILE"))return;
		if(!check_blank("#CERT_NO",'请输入身份证号！'))return;
		if(!check_str_len("输入长度不能超过40个字符！","#CERT_NO",40))return;
		if(!check_blank("#REAL_NAME",'请输入真实姓名！'))return;
		if(!check_str_len("输入长度不能超过40个字符！","#REAL_NAME",40))return;
		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}
	
</script>
	</head>
<body>
	<form action="msaccount/${msg }.do" name="Form" id="Form" method="post">
		<input type="hidden" name="ID" id="ID" value="${pd.ID}"/>
		<div id="zhongxin">
		<table id="table_report" class="table table-striped table-bordered table-hover">
			<tr>
				<td class="edit_td">用户名:</td>
				<td><input type="text" name="ACC_NAME" id="ACC_NAME" value="${pd.ACC_NAME}" maxlength="32" placeholder="这里输入用户名" title="用户名"/></td>
			</tr>
			<tr>
				<td class="edit_td">账号状态:</td>
				<td>
					<select name="STATUS" id="STATUS" data-placeholder="状态" class="select_top">
							<option <c:if test="${pd.STATUS==0}">selected</c:if> value="0">正常</option>
							<option <c:if test="${pd.STATUS==1}">selected</c:if> value="1">临时封号</option>
							<option <c:if test="${pd.STATUS==2}">selected</c:if> value="2">永久封号</option>
					</select>
			</tr>
			<tr>
				<td class="edit_td">VIP:</td>
				<td>
					<select name="VIP">
						<option <c:if test="${pd.VIP == 0}">selected</c:if> value="0">V0(普通用户)</option>
						<option <c:if test="${pd.VIP == 1}">selected</c:if> value="1">V1</option>
						<option <c:if test="${pd.VIP == 2}">selected</c:if> value="2">V2</option>
						<option <c:if test="${pd.VIP == 3}">selected</c:if> value="3">V3</option>
						<option <c:if test="${pd.VIP == 4}">selected</c:if> value="4">V4</option>
						<option <c:if test="${pd.VIP == 5}">selected</c:if> value="5">V5</option>
					</select>
				</td>
			</tr>
			<tr>
				<td class="edit_td">电话:</td>
				<td><input type="text" name="MOBILE" id="MOBILE" value="${pd.MOBILE}" maxlength="32" placeholder="这里输入电话" title="电话"/></td>
			</tr>
			<%--<tr>--%>
				<%--<td class="edit_td">邮箱:</td>--%>
				<%--<td><input type="text" name="EMAIL" id="EMAIL" value="${pd.EMAIL}" maxlength="32" placeholder="这里输入邮箱" title="邮箱"/></td>--%>
			<%--</tr>--%>
			<tr>
				<td class="edit_td">身份证号:</td>
				<td><input type="text" name="CERT_NO" id="CERT_NO" value="${pd.CERT_NO}" maxlength="32" placeholder="这里输入身份证号" title="身份证号"/></td>
			</tr>
			<tr>
				<td class="edit_td">真实姓名:</td>
				<td><input type="text" name="REAL_NAME" id="REAL_NAME" value="${pd.REAL_NAME}" maxlength="32" placeholder="这里输入真实姓名" title="真实姓名"/></td>
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
    	<script type="text/javascript" src="static/js/myjs/validate.js"></script>
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