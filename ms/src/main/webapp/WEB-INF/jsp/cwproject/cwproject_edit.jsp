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
		if(!check_num("#AMOUNT",'融资金额必须为正整数！'))return;
		if(!check_num("#MIN",'起投金额必须为正整数！'))return;
		if(!check_num("#STEP",'累加金额必须为正整数！'))return;
		if(!check_num("#DAYS",'持续天数必须为正整数！'))return;
		if(!check_num("#LIMIT",'限投金额必须为正整数！'))return;
		if(!check_num("#ANNUAL_RATE",'预期年化收益率必须为正数！'))return;
		if(!check_num("#SERVICE_RATE",'服务费率必须为正数！'))return;

		if($("#OPEN_DATE").val()==""){
			$("#OPEN_DATE").tips({
				side:3,
	            msg:'请输入开放购买时间',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#OPEN_DATE").focus();
			return false;
		}
		if($("#END_DATE").val()==""){
			$("#END_DATE").tips({
				side:3,
				msg:'请输入关闭时间',
				bg:'#AE81FF',
				time:2
			});
			$("#OPEN_DATE").focus();
			return false;
		}
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
	<form action="project/${msg }.do" name="Form" id="Form" method="post">
		<input type="hidden" name="ID" id="ID" value="${pd.ID}"/>
		<input type="hidden" name="PAY_INTEREST_WAY" id="PAY_INTEREST_WAY" value="1"/>
		<input type="hidden" name="GUARANTEE" id="GUARANTEE" value="1"/>
		<div id="zhongxin">
		<table id="table_report" class="table table-striped table-bordered table-hover">
			<tr>
				<td style="width:100px;text-align: right;padding-top: 13px;">项目名称:</td>
				<td><input type="text" name="NO" id="NO" value="${pd.NO}" maxlength="20" readonly title="项目名称"/></td>
			</tr>
			<%--<tr>--%>
				<%--<td style="width:100px;text-align: right;padding-top: 13px;">产品编号:</td>--%>
				<%--<td><input type="text" name="no" id="no" value="${pd.no}" maxlength="32" placeholder="产品编号" title="产品编号"/></td>--%>
			<%--</tr>--%>
			<%--<tr>--%>
				<%--<td style="width:100px;text-align: right;padding-top: 13px;">保障级别:</td>--%>
				<%--<td><input type="text" name="GUARANTEE" id="GUARANTEE" value="${pd.GUARANTEE}" maxlength="32" placeholder="保障级别" title="保障级别"/></td>--%>
			<%--</tr>--%>
			<%--<tr>--%>
				<%--<td style="width:100px;text-align: right;padding-top: 13px;">付息方式:</td>--%>
				<%--<td><input type="text" name="PAY_INTEREST_WAY" id="PAY_INTEREST_WAY" value="${pd.PAY_INTEREST_WAY}" maxlength="32" placeholder="付息方式" title="付息方式"/></td>--%>
			<%--</tr>--%>
			<tr>
				<td style="width:100px;text-align: right;padding-top: 13px;">融资金额:</td>
				<td>
					<input class="numFormat" type="number" name="AMOUNT" id="AMOUNT" value="${pd.AMOUNT}"
						   min="0"  maxlength="10" placeholder="融资金额（元）" title="融资金额（元）"/>
					<p style="color: #0a56c8"></p>
				</td>
			</tr>
			<tr>
				<td style="width:100px;text-align: right;padding-top: 13px;">持续天数:</td>
				<td><input type="number" name="DAYS" id="DAYS" value="${pd.DAYS}" min="0"  maxlength="10" placeholder="持续天数" title="持续天数"/></td>
			</tr>
			<tr>
				<td style="width:100px;text-align: right;padding-top: 13px;">起投金额:</td>
				<td><input class="numFormat" type="number" name="MIN" id="MIN" value="${pd.MIN}" min="0"  maxlength="10"
						   placeholder="起投金额（元）" title="起投金额（元）"/>
					<p style="color: #0a56c8"></p>
				</td>
			</tr>
			<tr>
				<td style="width:100px;text-align: right;padding-top: 13px;">累加金额:</td>
				<td><input class="numFormat" type="number" name="STEP" id="STEP" value="${pd.STEP}" min="0"
						   maxlength="10" placeholder="累加金额（元）" title="累加金额（元）"/>
					<p style="color: #0a56c8"></p>
				</td>
			</tr>
			<tr>
				<td style="width:100px;text-align: right;padding-top: 13px;">限投金额:</td>
				<td><input class="numFormat" type="number" name="LIMIT" id="LIMIT" value="${pd.LIMIT}" min="0"
						   maxlength="10" placeholder="限投金额（元）" title="限投金额（元）"/>
					<p style="color: #0a56c8"></p>
				</td>
			</tr>
			<tr>
				<td style="width:100px;text-align: right;padding-top: 13px;">预期年化收益率:</td>
				<td><input type="number" name="ANNUAL_RATE" id="ANNUAL_RATE" value="${pd.ANNUAL_RATE}" min="0" maxlength="10" step placeholder="预期年化收益率（%）" title="预期年化收益率（%）"/></td>
			</tr>
			<tr>
				<td style="width:100px;text-align: right;padding-top: 13px;">服务费率:</td>
				<td><input type="number" name="SERVICE_RATE" id="SERVICE_RATE" value="${pd.SERVICE_RATE}" min="0" maxlength="10" placeholder="服务费率（%）" title="服务费率（%）"/></td>
			</tr>
			<tr>
				<td style="width:100px;text-align: right;padding-top: 13px;">开放购买时间:</td>
				<td><input class=" datetime-picker" name="OPEN_DATE" id="OPEN_DATE" value="${pd.OPEN_DATE}"
						   type="text" data-date-format="yyyy-mm-dd hh:ii" readonly="readonly" maxlength="32" placeholder="开放购买时间"  title="开放购买时间"/></td>
				<%--<td><input type="text" name="OPEN_DATE" id="OPEN_DATE" value="${pd.OPEN_DATE}" maxlength="32" placeholder="开放购买时间" title="开放购买时间"/></td>--%>
			</tr>
			<tr>
				<td style="width:100px;text-align: right;padding-top: 13px;">关闭时间:</td>
				<td><input class=" datetime-picker" name="END_DATE" id="END_DATE" value="${pd.END_DATE}"
						   type="text" data-date-format="yyyy-mm-dd hh:ii" readonly="readonly" maxlength="32" placeholder="关闭时间"  title="关闭时间"/></td>
				<%--<td><input type="text" name="OPEN_DATE" id="OPEN_DATE" value="${pd.OPEN_DATE}" maxlength="32" placeholder="开放购买时间" title="开放购买时间"/></td>--%>
			</tr>
			<tr>
				<td style="width:100px;text-align: right;padding-top: 13px;">是否推荐:</td>
				<td>
					<select name="RECOMMEND">
						<option value="0">推荐</option>
						<option value="1">不推荐</option>
					</select>
				</td>
			</tr>
			<tr>
				<td style="width:100px;text-align: right;padding-top: 13px;">状态:</td>
				<td>
					<select name="STATUS" id="STATUS" data-placeholder="状态" class="select_top">
						<option <c:if test="${pd.STATUS==0}">selected</c:if> value="0">新建</option>
						<c:if test="${msg == 'edit'}">
							<option <c:if test="${pd.STATUS==1}">selected</c:if> value="1">发布</option>
							<option <c:if test="${pd.STATUS==2}">selected</c:if> value="2">已满标</option>
							<option <c:if test="${pd.STATUS==3}">selected</c:if> value="3">已结算</option>
							<option <c:if test="${pd.STATUS==4}">selected</c:if> value="4">已还款</option>
						</c:if>
					</select>
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