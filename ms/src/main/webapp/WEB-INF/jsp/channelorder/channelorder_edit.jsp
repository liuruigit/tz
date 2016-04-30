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
			check_blank("#AMOUNT",'请输入金额！');
		check_blank("#BANK_ID",'请输入银行卡ID！');
		check_blank("#ORDER_NO",'请输入请求订单号！');
		check_blank("#SYN_RESULT",'请输入同步应答码！');
		check_blank("#ASY_RESULT",'请输入异步应答码！');
		check_blank("#NOTIFY_TIME",'请输入通知时间！');
		check_blank("#TRAN_FLOW",'请输入外部订单号！');
		check_blank("#USER_ID",'请输入用户ID！');
		check_blank("#STATUS",'请输入0发起充值1平台接收处理中2到账！');
		check_blank("#CREATE_TIME",'请输入创建时间！');
		check_blank("#DELETE_STATE",'请输入1有效！');
		check_blank("#TYPE",'请输入订单类型0充值1提现！');
		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}
	
</script>
	</head>
<body>
	<form action="channelorder/${msg }.do" name="Form" id="Form" method="post">
		<input type="hidden" name="ID" id="ID" value="${pd.ID}"/>
		<div id="zhongxin">
		<table id="table_report" class="table table-striped table-bordered table-hover">
			<tr>
				<td class="edit_td">金额:</td>
				<td><input type="text" name="AMOUNT" id="AMOUNT" value="${pd.AMOUNT}" maxlength="32" placeholder="这里输入金额" title="金额"/></td>
			</tr>
			<tr>
				<td class="edit_td">银行卡ID:</td>
				<td><input type="text" name="BANK_ID" id="BANK_ID" value="${pd.BANK_ID}" maxlength="32" placeholder="这里输入银行卡ID" title="银行卡ID"/></td>
			</tr>
			<tr>
				<td class="edit_td">请求订单号:</td>
				<td><input type="text" name="ORDER_NO" id="ORDER_NO" value="${pd.ORDER_NO}" maxlength="32" placeholder="这里输入请求订单号" title="请求订单号"/></td>
			</tr>
			<tr>
				<td class="edit_td">同步应答码:</td>
				<td><input type="text" name="SYN_RESULT" id="SYN_RESULT" value="${pd.SYN_RESULT}" maxlength="32" placeholder="这里输入同步应答码" title="同步应答码"/></td>
			</tr>
			<tr>
				<td class="edit_td">异步应答码:</td>
				<td><input type="text" name="ASY_RESULT" id="ASY_RESULT" value="${pd.ASY_RESULT}" maxlength="32" placeholder="这里输入异步应答码" title="异步应答码"/></td>
			</tr>
			<tr>
				<td class="edit_td">通知时间:</td>
				<td><input type="text" name="NOTIFY_TIME" id="NOTIFY_TIME" value="${pd.NOTIFY_TIME}" maxlength="32" placeholder="这里输入通知时间" title="通知时间"/></td>
			</tr>
			<tr>
				<td class="edit_td">外部订单号:</td>
				<td><input type="text" name="TRAN_FLOW" id="TRAN_FLOW" value="${pd.TRAN_FLOW}" maxlength="32" placeholder="这里输入外部订单号" title="外部订单号"/></td>
			</tr>
			<tr>
				<td class="edit_td">用户ID:</td>
				<td><input type="text" name="USER_ID" id="USER_ID" value="${pd.USER_ID}" maxlength="32" placeholder="这里输入用户ID" title="用户ID"/></td>
			</tr>
			<tr>
				<td class="edit_td">0发起充值1平台接收处理中2到账:</td>
				<td><input type="text" name="STATUS" id="STATUS" value="${pd.STATUS}" maxlength="32" placeholder="这里输入0发起充值1平台接收处理中2到账" title="0发起充值1平台接收处理中2到账"/></td>
			</tr>
			<tr>
				<td class="edit_td">创建时间:</td>
				<td><input type="text" name="CREATE_TIME" id="CREATE_TIME" value="${pd.CREATE_TIME}" maxlength="32" placeholder="这里输入创建时间" title="创建时间"/></td>
			</tr>
			<tr>
				<td class="edit_td">1有效:</td>
				<td><input type="text" name="DELETE_STATE" id="DELETE_STATE" value="${pd.DELETE_STATE}" maxlength="32" placeholder="这里输入1有效" title="1有效"/></td>
			</tr>
			<tr>
				<td class="edit_td">订单类型0充值1提现:</td>
				<td><input type="text" name="TYPE" id="TYPE" value="${pd.TYPE}" maxlength="32" placeholder="这里输入订单类型0充值1提现" title="订单类型0充值1提现"/></td>
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