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
		<link href="static/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
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
		if(!check_blank("#NAME",'请输入标题名称！'))return;
		if(!check_str_len("标题在30个字符以内", "#NAME", 30))return;
		if(!check_str_len("描述在100个字符以内", "#DESC", 100))return;
		if(!check_blank("#MIN",'请输入金额下限！'))return;
		if(!check_num("#MIN",'金额下限必须为正整数！'))return;
		if(!check_blank("#BEGIN_DATE",'请输入开始日期！'))return;
		if(!check_blank("#END_DATE",'请输入截止日期！'))return;
		if(!check_blank("#TAG",'请输入适用范围！'))return;
		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}
	
</script>
	</head>
<body>
	<form action="coupon/${msg }.do" name="Form" id="Form" method="post">
		<input type="hidden" name="ID" id="ID" value="${pd.ID}"/>
		<div id="zhongxin">
		<table id="table_report" class="table table-striped table-bordered table-hover">
			<tr>
				<td class="edit_td">标题:</td>
				<td><input type="text" name="NAME" id="NAME" value="${pd.NAME}" maxlength="30" placeholder="请输入标题" title="标题"/></td>
			</tr>
			<tr>
				<td class="edit_td">描述:</td>
				<td><textarea type="text" name="DESC" id="DESC" rows="8" style="margin: 0px 0px 10px; width:
				 314px; height: 177px;" maxlength="300" placeholder="描述" title="描述">${pd.DESC}</textarea></td>
			</tr>
			<tr>
				<td class="edit_td">金额下限:</td>
				<td><input type="text" name="MIN" id="MIN" value="${pd.MIN}" maxlength="32" placeholder="请输入金额下限" title="金额下限"/></td>
			</tr>
			<tr>
				<td class="edit_td">开始日期:</td>
				<td><input class="span10 date-picker" name="BEGIN_DATE" id="BEGIN_DATE" value="${pd.BEGIN_DATE}"
						   type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="开始日期"/></td>
			</tr>
			<tr>
				<td class="edit_td">截止日期:</td>
				<td><input class="span10 date-picker" name="END_DATE" id="END_DATE" value="${pd.END_DATE}" type="text"
						   data-date-format="yyyy-mm-dd"
						   readonly="readonly" style="width:88px;" placeholder="截止日期"/></td>
			</tr>
			<tr>
				<td class="edit_td">适用范围:</td>
				<td><input type="text" name="TAG" id="TAG" value="${pd.TAG}" maxlength="32" placeholder="请输入适用范围" title="适用范围"/></td>
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
		<script type="text/javascript" src="static/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
		<script type="text/javascript" src="static/js/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
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