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
		check_blank("#BANK_SHORT_NAME",'请输入银行简称！');
		check_blank("#LIMIT",'请输入单笔限额！');
		check_blank("#DAY_LIMIT",'请输入单日限额！');
		check_blank("#TIME_LIMIT_BEGIN",'请输入起始时间！');
		check_blank("#TIME_LIMIT_END",'请输入结束时间！');
		check_blank("#CHANNEL_NAME",'请输入渠道名称！');
		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}

</script>
	</head>
<body>
	<form action="bankrule/${msg }.do" name="Form" id="Form" method="post">
		<input type="hidden" name="ID" id="ID" value="${pd.ID}"/>
		<div id="zhongxin">
		<table id="table_report" class="table table-striped table-bordered table-hover">
			<tr>
				<td class="edit_td">银行名称:</td>
				<td><input type="text" name="BANK_NAME" id="BANK_NAME" value="${pd.BANK_NAME}" maxlength="32" placeholder="这里输入银行名称" title="银行名称"/></td>
			</tr>
			<tr>
				<td class="edit_td">银行简称:</td>
				<td><input type="text" name="BANK_SHORT_NAME" id="BANK_SHORT_NAME" value="${pd.BANK_SHORT_NAME}" maxlength="32" placeholder="这里输入银行简称" title="银行简称"/></td>
			</tr>
			<tr>
				<td class="edit_td">单笔限额:</td>
				<td><input type="text" name="LIMIT" id="LIMIT" value="${pd.LIMIT}" maxlength="32" placeholder="这里输入单笔限额" title="单笔限额"/></td>
			</tr>
			<tr>
				<td class="edit_td">单日限额:</td>
				<td><input type="text" name="DAY_LIMIT" id="DAY_LIMIT" value="${pd.DAY_LIMIT}" maxlength="32" placeholder="这里输入单日限额" title="单日限额"/></td>
			</tr>
			<%--<tr>--%>
				<%--<td class="edit_td">起始时间:</td>--%>
				<%--<td><input class="span10 date-picker" name="TIME_LIMIT_BEGIN" id="TIME_LIMIT_BEGIN" value="<fmt:formatDate value='${pd.TIME_LIMIT_BEGIN}' pattern='yyyy-MM-dd'/>" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:70%;" placeholder="开始日期"/></td>--%>
			<%--</tr>--%>
			<%--<tr>--%>
				<%--<td class="edit_td">结束时间:</td>--%>
				<%--<td><input class="span10 date-picker" name="TIME_LIMIT_END" id="TIME_LIMIT_END" value="<fmt:formatDate value='${pd.TIME_LIMIT_END}' pattern='yyyy-MM-dd'/>" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:70%;" placeholder="开始日期"/></td>--%>
			<%--</tr>--%>
			<tr>
				<td class="edit_td">渠道名称:</td>
				<td><input type="text" name="CHANNEL_NAME" id="CHANNEL_NAME" value="${pd.CHANNEL_NAME}" maxlength="32" placeholder="这里输入渠道名称" title="渠道名称"/></td>
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