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
		if(!check_blank("#DAYS",'请输入期限！'))return;
		if(!check_num("#DAYS", "期限只能输入正整数！"))return;
		if(!check_blank("#RANGE_START",'请输入投资金额（起）！'))return;
		if(!check_num("#RANGE_START", "投资金额（起）只能输入正整数！"))return;
		if(!check_blank("#RANGE_END",'请输入投资金额（止）！'))return;
		if(!check_num("#RANGE_END", "投资金额（止）只能输入正整数！"))return;
		if($("#RANGE_START").val() >= $("#RANGE_END").val()) {
			tail("#RANGE_END", "起始金额必须小于终止金额!");
			return false;
		}
		if(!check_blank("#PERC",'请输入分成比例！'))return;
		if(!check_num("#PERC",'分成比例为正数！'))return;
		if(!check_blank("#IS_OPEN",'请输入是否开启！'))return;
		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}
	
</script>
	</head>
<body>
	<form action="inviterewardconfig/${msg }.do" name="Form" id="Form" method="post">
		<input type="hidden" name="ID" id="ID" value="${pd.ID}"/>
		<div id="zhongxin">
		<table id="table_report" class="table table-striped table-bordered table-hover">
			<tr>
				<td class="edit_td">期限:</td>
				<td><input type="text" name="DAYS" id="DAYS" value="${pd.DAYS}" maxlength="32" placeholder="这里输入期限" title="期限"/></td>
			</tr>
			<tr>
				<td class="edit_td">投资金额（起）:</td>
				<td><input type="text" name="RANGE_START" id="RANGE_START" value="${pd.RANGE_START}" maxlength="32" placeholder="这里输入投资金额（起）" title="投资金额（起）"/></td>
			</tr>
			<tr>
				<td class="edit_td">投资金额（止）:</td>
				<td><input type="text" name="RANGE_END" id="RANGE_END" value="${pd.RANGE_END}" maxlength="32" placeholder="这里输入投资金额（止）" title="投资金额（止）"/></td>
			</tr>
			<tr>
				<td class="edit_td">分成比例:</td>
				<td><input type="text" name="PERC" id="PERC" value="${pd.PERC}" maxlength="32" placeholder="这里输入分成比例" title="分成比例"/></td>
			</tr>
			<tr>
				<td class="edit_td">是否开启:</td>
				<td>
					<select name="IS_OPEN" id="IS_OPEN" data-placeholder="是否开启" class="select_top">
						<option <c:if test="${pd.IS_OPEN==0}">selected</c:if> value="0">开启</option>
						<option <c:if test="${pd.IS_OPEN==1}">selected</c:if> value="1">关闭</option>
					</select>
				</td>
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