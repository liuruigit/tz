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
		if(!check_blank("#ACC_ID",'请输入用户ID！'))return;
		if(!check_str_len("用户ID", "#ACC_ID", 10))return;
		if(!check_blank("#AMOUNT",'请输入金额！'))return;
		if(!check_num("#AMOUNT", "请输入正整数！"))return;
		if(!check_blank("#COUPON_ID",'请输入投资券ID！'))return;
		if(!check_str_len("用户ID", "#COUPON_ID", 10))return;
		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}
	
</script>
	</head>
<body>
	<form action="acccoupon/${msg }.do" name="Form" id="Form" method="post">
		<input type="hidden" name="ID" id="ID" value="${pd.ID}"/>
		<div id="zhongxin">
		<table id="table_report" class="table table-striped table-bordered table-hover">
			<tr>
				<td class="edit_td">用户ID:</td>
				<td><input type="text" onblur="checkNo()" name="ACC_ID" id="ACC_ID" value="${pd.ACC_ID}" maxlength="32" placeholder="这里输入用户ID" title="用户ID"/></td>
			</tr>
			<tr>
				<td class="edit_td">金额:</td>
				<td><input type="text" name="AMOUNT" id="AMOUNT" value="${pd.AMOUNT}" maxlength="32" placeholder="这里输入金额" title="金额"/></td>
			</tr>
			<tr>
				<td class="edit_td">是否使用:</td>
				<td>
					<select name="IS_USED" id="IS_USED" data-placeholder="请选择" style="vertical-align:top;width: 70%;">
						<option <c:if test="${pd.IS_USED == 0}">selected</c:if> value="0">未使用</option>
						<option <c:if test="${pd.IS_USED == 1}">selected</c:if> value="1">已使用</option>
					</select>
				</td>
			</tr>
			<tr>
				<td class="edit_td">投资券ID:</td>
				<td><input type="text" name="COUPON_ID" onblur="checkInvest()" id="COUPON_ID" value="${pd.COUPON_ID}" maxlength="32" placeholder="这里输入投资券ID" title="投资券ID"/></td>
			</tr>
			<tr>
				<td style="width:100px;text-align: right;padding-top: 13px;">有效日期:</td>
				<td><input class=" datetime-picker" name="VALID_DATE" id="VALID_DATE" value="${pd.VALID_DATE}"  type="text" data-date-format="yyyy-mm-dd hh:ii" readonly="readonly" maxlength="32" placeholder="有效日期"  title="有效日期"/>
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
			$('.datetime-picker').datetimepicker({
				language: 'zh-CN',
				weekStart: 1,
				todayBtn:  1,
				autoclose: 1,
				todayHighlight: 1,
				startView: 2,
				forceParse: 0,
				showMeridian: 1
			});
			
		});

		function checkNo(){
			var val = $('#ACC_ID').val();
			$.get('<%=basePath%>acccoupon/findUserByNo.do?ID='+val,function(data){
				if(data.code == 0) {

				} else if(data.code == 1){
					$("#ACC_ID").tips({
						side:3,
						msg:'该用户不存在',
						bg:'#AE81FF',
						time:2
					});
					$('#ACC_ID').val('');
					$("#ACC_ID").focus();
				} else {
					$("#ACC_ID").tips({
						side:3,
						msg:'操作有误！',
						bg:'#AE81FF',
						time:2
					});
					$('#ACC_ID').val('');
					$("#ACC_ID").focus();
				}
			});
		}

		function checkInvest(){
			var val = $('#COUPON_ID').val();
			$.get('<%=basePath%>acccoupon/findCouponByNo.do?ID='+val,function(data){
				if(data.code == 0) {

				} else if(data.code == 1){
					$("#COUPON_ID").tips({
						side:3,
						msg:'该优惠券不存在',
						bg:'#AE81FF',
						time:2
					});
					$('#COUPON_ID').val('');
					$("#COUPON_ID").focus();
				} else if(data.code == 2){
					$("#COUPON_ID").tips({
						side:3,
						msg:'该优惠券已被使用！',
						bg:'#AE81FF',
						time:2
					});
					$('#COUPON_ID').val('');
					$("#COUPON_ID").focus();
				} else {
					$("#COUPON_ID").tips({
						side:3,
						msg:'操作有误！',
						bg:'#AE81FF',
						time:2
					});
					$('#COUPON_ID').val('');
					$("#COUPON_ID").focus();
				}
			});
		}
		</script>
</body>
</html>