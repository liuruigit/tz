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
		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}

	function cancel(){
		$("#Form").attr('action',"unbindingapply/cancel.do");
		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}
</script>
	</head>
<body>
	<form action="unbindingapply/${msg }.do" name="Form" id="Form" method="post">
		<input type="hidden" name="ACC_ID" id="ACC_ID" value="${pd.ACC_ID}"/>
		<input type="hidden" name="ID" id="ID" value="${pd.ID}"/>
		<input type="hidden" name="bankcard_id" id="bankcard_id" value="${pd.bankcard_id}"/>
        <input type="hidden" name="mobile" id="mobile" value="${pd.mobile}"/>
		<div id="zhongxin">
		<table id="table_report" class="table table-striped table-bordered table-hover">

            <tr>
                <td class="edit_td">姓 名:</td>
                <td>${pd.name}</td>
            </tr>
            <tr>
                <td class="edit_td">银 行:</td>
                <td>${pd.bank_name}</td>
            </tr>
            <tr>
                <td class="edit_td">卡 号:</td>
                <td>${pd.bank_card_no}</td>
            </tr>
            <tr>
                <td class="edit_td">消息标题:</td>
                <td><input type="text" name="msgTitle" id="msgTitle" value="${pd.msgTitle}" maxlength="32" placeholder="消息标题" title="消息标题"/></td>
            </tr>
            <tr>
                <td class="edit_td">消息内容:</td>
                <td><textarea type="text" name="msgContent" id="msgContent" value="${pd.msgContent}" rows="6" cols="120" placeholder="消息内容" title="消息内容"></textarea></td>
            </tr>
			<tr>
				<td style="text-align: center;" colspan="10">
					<a class="btn btn-mini btn-primary" onclick="save();">通过</a>
					<a class="btn btn-mini btn-primary" onclick="cancel();">拒绝</a>
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