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
		<script type="text/javascript" src="static/js/myjs/validate.js"></script>NO

<script type="text/javascript">
	//保存
	function save(){
		check_blank("#ACC_NAME","请输入手机号！");
		check_blank("#PWD","请输入密码！");
		check_blank("#TRADE_PWD","请输入交易密码！");
		$("#REAL_NAME").val() == '' ? false :check_blank("#REAL_NAME","请输入真实姓名！");
		$("#EMAIL").val() == '' ? false :check_email("#EMAIL");
		$("#CERT_NO").val() == '' ? false :check_idCard("#CERT_NO","请输入合法身份证号!");
		$("#MONEY").val() == '' ? false :check_double("#MONEY","请输入余额!");
		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}
	
</script>
	</head>
<body>
	<form action="client/${msg }.do" name="Form" id="Form" method="post">
		<input type="hidden" name="USER_ID" id="USER_ID" value="${pd.USER_ID}"/>
		<div id="zhongxin">
		<table id="table_report" class="table table-striped table-bordered table-hover">
			<tr>
				<td class="edit_td">手机号:</td>

				<td><input type="text" name="ACC_NAME" id="ACC_NAME" value="${pd.ACC_NAME}" maxlength="20" placeholder="手机号" title="手机号"/></td>
				<td><input id="private_phone" name="private_phone" pattern="(\d{11})|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})
				-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$" required="required" type="text" /></td>
			</tr>
			<tr>
				<td class="edit_td">密码:</td>
				<td><input type="password" name="PWD" id="PWD" value="${pd.PWD}" maxlength="20" placeholder="密码" title="密码"/></td>
			</tr>
			<tr>
				<td class="edit_td">交易密码:</td>
				<td><input type="password" name="TRADE_PWD" id="TRADE_PWD" value="${pd.TRADE_PWD}" maxlength="32" placeholder="交易密码" title="交易密码"/></td>
			</tr>
			<%--<tr>--%>
				<%--<td class="edit_td">手机号:</td>--%>
				<%--<td><input type="text" name="MOBILE" id="MOBILE" value="${pd.MOBILE}" maxlength="32" placeholder="手机号" title="手机号"/></td>--%>
			<%--</tr>--%>
			<tr>
				<td class="edit_td">邮箱:</td>
				<td><input type="email" name="EMAIL" id="EMAIL" value="${pd.EMAIL}" maxlength="50" placeholder="邮箱" title="邮箱"/></td>
			</tr>
			<tr>
				<td class="edit_td">身份证号:</td>
				<td><input type="text" name="CERT_NO" id="CERT_NO" value="${pd.CERT_NO}" maxlength="32" placeholder="身份证号" title="身份证号"/></td>
			</tr>
			<tr>
				<td class="edit_td">真实姓名:</td>
				<td><input type="text" name="REAL_NAME" id="REAL_NAME" value="${pd.REAL_NAME}" maxlength="32" placeholder="真实姓名" title="真实姓名"/></td>
			</tr>
			<tr>
				<td class="edit_td">余额:</td>
				<td><input type="text" name="MONEY" id="MONEY" value="${pd.MONEY}" maxlength="32" placeholder="余额" title="余额"/></td>
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