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
			check_blank("#BANK_NAME",'请输入银行名称！');
		check_blank("#NAME",'请输入名称！');
		check_blank("#USER_ID",'请输入用户ID！');
		check_blank("#BANK_CARD_NO",'请输入银行卡号！');
		check_blank("#PRO",'请输入省份！');
		check_blank("#CITY",'请输入城市！');
		check_blank("#BRANCH",'请输入支行！');
		check_blank("#BANK_CODE",'请输入code！');
		check_blank("#IS_DEFAULT",'请输入是否默认卡！');
		check_blank("#PRCPTCD",'请输入协议号！');
		check_blank("#NO_AGREE",'请输入签约协议号！');
		check_blank("#MOBILE",'请输入绑定的手机号！');
		check_blank("#CREATE_TIME",'请输入创建时间！');
		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}
	
</script>
	</head>
<body>
	<form action="bankcard/${msg }.do" name="Form" id="Form" method="post">
		<input type="hidden" name="ID" id="ID" value="${pd.ID}"/>
		<div id="zhongxin">
		<table id="table_report" class="table table-striped table-bordered table-hover">
			<tr>
				<td class="edit_td">银行名称:</td>
				<td><input type="text" name="BANK_NAME" id="BANK_NAME" value="${pd.BANK_NAME}" maxlength="32" placeholder="这里输入银行名称" title="银行名称"/></td>
			</tr>
			<tr>
				<td class="edit_td">名称:</td>
				<td><input type="text" name="NAME" id="NAME" value="${pd.NAME}" maxlength="32" placeholder="这里输入名称" title="名称"/></td>
			</tr>
			<tr>
				<td class="edit_td">用户ID:</td>
				<td><input type="text" name="USER_ID" id="USER_ID" value="${pd.USER_ID}" maxlength="32" placeholder="这里输入用户ID" title="用户ID"/></td>
			</tr>
			<tr>
				<td class="edit_td">银行卡号:</td>
				<td><input type="text" name="BANK_CARD_NO" id="BANK_CARD_NO" value="${pd.BANK_CARD_NO}" maxlength="32" placeholder="这里输入银行卡号" title="银行卡号"/></td>
			</tr>
			<tr>
				<td class="edit_td">省份:</td>
				<td><input type="text" name="PRO" id="PRO" value="${pd.PRO}" maxlength="32" placeholder="这里输入省份" title="省份"/></td>
			</tr>
			<tr>
				<td class="edit_td">城市:</td>
				<td><input type="text" name="CITY" id="CITY" value="${pd.CITY}" maxlength="32" placeholder="这里输入城市" title="城市"/></td>
			</tr>
			<tr>
				<td class="edit_td">支行:</td>
				<td><input type="text" name="BRANCH" id="BRANCH" value="${pd.BRANCH}" maxlength="32" placeholder="这里输入支行" title="支行"/></td>
			</tr>
			<tr>
				<td class="edit_td">code:</td>
				<td><input type="text" name="BANK_CODE" id="BANK_CODE" value="${pd.BANK_CODE}" maxlength="32" placeholder="这里输入code" title="code"/></td>
			</tr>
			<tr>
				<td class="edit_td">是否默认卡:</td>
				<td><input type="text" name="IS_DEFAULT" id="IS_DEFAULT" value="${pd.IS_DEFAULT}" maxlength="32" placeholder="这里输入是否默认卡" title="是否默认卡"/></td>
			</tr>
			<tr>
				<td class="edit_td">协议号:</td>
				<td><input type="text" name="PRCPTCD" id="PRCPTCD" value="${pd.PRCPTCD}" maxlength="32" placeholder="这里输入协议号" title="协议号"/></td>
			</tr>
			<tr>
				<td class="edit_td">签约协议号:</td>
				<td><input type="text" name="NO_AGREE" id="NO_AGREE" value="${pd.NO_AGREE}" maxlength="32" placeholder="这里输入签约协议号" title="签约协议号"/></td>
			</tr>
			<tr>
				<td class="edit_td">绑定的手机号:</td>
				<td><input type="text" name="MOBILE" id="MOBILE" value="${pd.MOBILE}" maxlength="32" placeholder="这里输入绑定的手机号" title="绑定的手机号"/></td>
			</tr>
			<tr>
				<td class="edit_td">创建时间:</td>
				<td><input type="text" name="CREATE_TIME" id="CREATE_TIME" value="${pd.CREATE_TIME}" maxlength="32" placeholder="这里输入创建时间" title="创建时间"/></td>
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
    	<script type="text/javascript" src="static/js/myjs/validate.js"></script>NO
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