<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<base href="<%=basePath%>">
		<meta charset="utf-8" />
		<title></title>
		<meta name="description" content="overview & stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<link href="<%=basePath%>static/css/bootstrap.min.css" rel="stylesheet" />
		<link href="<%=basePath%>static/css/bootstrap-responsive.min.css" rel="stylesheet" />
		<link rel="<%=basePath%>stylesheet" href="static/css/font-awesome.min.css" />
		<!-- 下拉框 -->
		<link rel="stylesheet" href="<%=basePath%>static/css/chosen.css" />
		
		<link rel="stylesheet" href="<%=basePath%>static/css/ace.min.css" />
		<link rel="stylesheet" href="<%=basePath%>static/css/ace-responsive.min.css" />
		<link rel="stylesheet" href="<%=basePath%>static/css/ace-skins.min.css" />
		
		<link rel="stylesheet" href="<%=basePath%>static/css/datepicker.css" /><!-- 日期框 -->
		<script type="text/javascript" src="<%=basePath%>static/js/jquery-1.7.2.js"></script>
		<script type="text/javascript" src="<%=basePath%>static/js/jquery.tips.js"></script>
		
<script type="text/javascript">
	
	
	//保存
	function save(){
		if(!check_blank("#GS_CODE",'请输入统一社会信用代码！'))return false;
		if(!check_str_len("统一社会信用代码", "#GS_CODE", 50))return false;
		if(!check_blank("#LOCATION",'请输入经营场所！'))return false;
		if(!check_str_len("经营场所", "#LOCATION", 50))return false;
		if(!check_blank("#NAME",'请输入甲方名称！'))return false;
		if(!check_str_len("经营场所", "#NAME", 20))return false;
		if(!check_blank("#TOTAL_AMOUNT",'请输入合伙人认缴出资！'))return false;
		if(!check_num("#TOTAL_AMOUNT", "请输入正整数！"))return false;
		if(!check_blank("#REAL_TOTAL_AMOUNT",'请输入合伙人实缴！'))return false;
		if(!check_num("#REAL_TOTAL_AMOUNT", "请输入正整数！"))return false;
		if(!check_blank("#SUPPLIER_REAL_AMOUNT",'请输入甲方已实缴！'))return false;
		if(!check_num("#SUPPLIER_REAL_AMOUNT", "请输入正整数！"))return false;
		if(!check_blank("#SUPPLIER_HOLD_PERC",'请输入甲方持有财产份额！'))return false;
		if(!check_blank("#PRO_ID",'请输输入标的！'))return false;
		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}
	
</script>
	</head>
<body>
	<form action="supplierprojectmapping/${msg }.do" name="Form" id="Form" method="post">
		<input type="hidden" name="ID" id="ID" value="${pd.ID}"/>
		<div id="zhongxin">
		<table id="table_report" class="table table-striped table-bordered table-hover">
			<tr>
				<td class="edit_td">统一社会信用代码:</td>
				<td><input type="text" name="GS_CODE" id="GS_CODE" value="${pd.GS_CODE}" maxlength="32" placeholder="这里输入统一社会信用代码" title="统一社会信用代码"/></td>
			</tr>
			<tr>
				<td class="edit_td">经营场所:</td>
				<td><input type="text" name="LOCATION" id="LOCATION" value="${pd.LOCATION}" maxlength="32" placeholder="这里输入经营场所" title="经营场所"/></td>
			</tr>
			<tr>
				<td class="edit_td">甲方名称:</td>
				<td><input type="text" name="NAME" id="NAME" value="${pd.NAME}" maxlength="32" placeholder="这里输入甲方名称" title="甲方名称"/></td>
			</tr>
			<tr>
				<td class="edit_td">合伙人认缴出资:</td>
				<td><input type="text" name="TOTAL_AMOUNT" id="TOTAL_AMOUNT" value="${pd.TOTAL_AMOUNT}" maxlength="32" placeholder="这里输入合伙人认缴出资" title="合伙人认缴出资"/></td>
			</tr>
			<tr>
				<td class="edit_td">合伙人实缴:</td>
				<td><input type="text" name="REAL_TOTAL_AMOUNT" id="REAL_TOTAL_AMOUNT" value="${pd.REAL_TOTAL_AMOUNT}" maxlength="32" placeholder="这里输入合伙人实缴" title="合伙人实缴"/></td>
			</tr>
			<tr>
				<td class="edit_td">甲方已实缴:</td>
				<td><input type="text" name="SUPPLIER_REAL_AMOUNT" id="SUPPLIER_REAL_AMOUNT" value="${pd.SUPPLIER_REAL_AMOUNT}" maxlength="32" placeholder="这里输入甲方已实缴" title="甲方已实缴"/></td>
			</tr>
			<tr>
				<td class="edit_td">甲方持有财产份额:</td>
				<td><input type="text" name="SUPPLIER_HOLD_PERC" id="SUPPLIER_HOLD_PERC" value="${pd.SUPPLIER_HOLD_PERC}" maxlength="32" placeholder="这里输入甲方持有财产份额" title="甲方持有财产份额"/></td>
			</tr>
			<tr>
				<td class="edit_td">标的ID:</td>
				<td><input type="text" name="PRO_ID" id="PRO_ID" value="${pd.PRO_ID}" maxlength="32" placeholder="这里输入配置value" title="配置value"/></td>
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
		<script type="text/javascript">window.jQuery || document.write("<script src='<%=basePath%>static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
		<script src="<%=basePath%>static/js/bootstrap.min.js"></script>
		<script src="<%=basePath%>static/js/ace-elements.min.js"></script>
		<script src="<%=basePath%>static/js/ace.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>static/js/chosen.jquery.min.js"></script><!-- 下拉框 -->
		<script type="text/javascript" src="<%=basePath%>static/js/bootstrap-datepicker.min.js"></script><!-- 日期框 -->
    	<script type="text/javascript" src="<%=basePath%>static/js/myjs/validate.js"></script>
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