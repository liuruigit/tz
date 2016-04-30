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

	function isPositiveNum(s){//是否为正整数
		var re = /^[1-9][0-9]*$/ ;
		return re.test(s)
	}
	
	//保存
	function save(){
//		check("#AMOUNT");
		if(!check_num("#FINAL_AMOUNT",'结算金额必须为正整数！'))return;
		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}

	function check(obj){
		var reg = /^[1-9][1-9]*/;
		alert(reg.test($(obj).val()));
	}
</script>
	</head>
<body>
	<form action="hzfProject/settlement.do" name="Form" id="Form" method="post">
		<input type="hidden" name="ID" id="ID" value="${pd.ID}"/>
		<div id="zhongxin">
		<table id="table_report" class="table table-striped table-bordered table-hover">
			<tr>
				<td style="width:100px;text-align: right;padding-top: 13px;">结算金额:</td>
				<td>
					<input class="numFormat" type="number" name="FINAL_AMOUNT" id="FINAL_AMOUNT" value="${pd.FINAL_AMOUNT}"
						   min="0"  maxlength="10" placeholder="结算金额（元）" title="结算金额（元）"/>
					<p style="color: #0a56c8"></p>
				</td>
			</tr>
			<%--<tr>--%>
				<%--<td style="width:100px;text-align: right;padding-top: 13px;">预留信息1:</td>--%>
				<%--<td><input type="text" name="EXTRA1" id="EXTRA1" value="${pd.EXTRA1}" maxlength="32" placeholder="预留信息1" title="预留信息1"/></td>--%>
			<%--</tr>--%>
			<%--<tr>--%>
				<%--<td style="width:100px;text-align: right;padding-top: 13px;">预留信息2:</td>--%>
				<%--<td><input type="text" name="EXTRA2" id="EXTRA2" value="${pd.EXTRA2}" maxlength="32" placeholder="预留信息2" title="预留信息2"/></td>--%>
			<%--</tr>--%>
			<%--<tr>--%>
				<%--<td style="width:100px;text-align: right;padding-top: 13px;">预留信息3:</td>--%>
				<%--<td><input type="text" name="EXTRA3" id="EXTRA3" value="${pd.EXTRA3}" maxlength="32" placeholder="预留信息3" title="预留信息3"/></td>--%>
			<%--</tr>--%>
			<tr>
				<td style="text-align: center;" colspan="10">
					<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
					<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
				</td>
			</tr>
		</table>
		</div>
		<%--<div class="form-group">--%>
			<%--<label for="dtp_input1" class="col-md-2 control-label">DateTime Picking</label>--%>
			<%--<div class="input-group date form_datetime col-md-5" data-date-format="dd MM yyyy - HH:ii" data-link-field="dtp_input1">--%>
				<%--<input class="form-control" size="16" type="text" value="">--%>
				<%--<span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>--%>
				<%--<span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>--%>
			<%--</div>--%>
			<%--<input type="hidden" id="dtp_input1" value="" /><br/>--%>
		<%--</div>--%>
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

			$(".numFormat").keyup(function(){
				if(testNum(this)) {
					var val = $(this).val();
					var char = digitUppercase(val);
					$(this).next().text(char)
				}
			});

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
		
		</script>
</body>
</html>