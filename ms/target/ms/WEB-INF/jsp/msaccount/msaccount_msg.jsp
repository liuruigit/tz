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
			check_blank("#ACC_NAME",'请输入用户名！');
		check_blank("#PWD",'请输入密码！');
		check_blank("#TRADE_PWD",'请输入交易密码！');
		check_blank("#STATUS",'请输入账号状态！');
		check_blank("#MOBILE",'请输入电话！');
		check_blank("#EMAIL",'请输入邮箱！');
		check_blank("#CERT_NO",'请输入身份证号！');
		check_blank("#REAL_NAME",'请输入真实姓名！');
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
		<table id="table_report" class="table table-striped">
			<tr>
				<td class="edit_td" colspan="10"><textarea name="content" id="content" placeholder="编辑短信内容..." title="短信内容" style="width:97%; height: 150px;;"></textarea><br/><span style="color: red;">*</span><span>发送内容不超过100字！</span></td>
			<tr>
			<tr>
				<td style="text-align: right;" colspan="5">
					<label>
						<input type='checkbox' name='msgType' value="0" /><span class="lbl">短信</span>
					</label>
				</td>
				<td style="text-align: left;" colspan="5">
					<label>
						<input type='checkbox' name='msgType' value="1" /><span class="lbl">消息</span>
					</label>
				</td>
			</tr>
			<tr>
				<td style="text-align: center;" colspan="10">
					<a class="btn btn-mini btn-primary" onclick="sendMSG();">发送</a>
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
		<script type="text/javascript" src="static/js/bootbox.min.js"></script><!-- 确认窗口 -->
		<script type="text/javascript" src="static/js/myjs/validate.js"></script>
		<script type="text/javascript" src="static/js/jquery.tips.js"></script><!--提示框-->
		<script type="text/javascript">

		//短信内容发送
		function sendMSG() {
			var id = $("#ID").val();
			var content = $("#content").val();
			var msgType = new Array();
			$("input[name='msgType']:checked").each(function() {
			     msgType.push($(this).val());
			 });
			if($.trim(content) == "" || content == null) {
				nextPage("发送内容不能为空！");
			} else if(content.length > 100) {
				nextPage("发送内容不能超过100字！");
			} else if(msgType.length <= 0){
				nextPage("请选择发送类型！");
			} else {
				var url = $("#Form").attr("action");
				var reqJSON = {'ID':id,'CONTENT':content, 'MSGTYPE':msgType.join(",")};
				$.post(url, reqJSON, function(result) {
					alert(result.message);
					top.Dialog.close();
				});
			}
		}


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