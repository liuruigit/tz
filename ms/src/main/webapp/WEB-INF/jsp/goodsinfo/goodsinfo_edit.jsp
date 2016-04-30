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
			check_blank("#NAME",'请输入商品名称！');
		check_blank("#INTRODUCE",'请输入商品介绍！');
		check_blank("#DESC",'请输入商品描述！');
		check_blank("#PRICE",'请输入商品原价！');
		check_blank("#DISCOUNT_PRICE",'请输入折扣价！');
		check_blank("#SALES_NUM",'请输入销售量！');
		check_blank("#IMAGE_URL",'请输入介绍图地址！');
		check_blank("#BANNEL_URL",'请输入bannel地址！');
		check_blank("#TYPE",'请输入商品类型！');
		check_blank("#STATUS",'请输入商品状态！');
		check_blank("#REMARK",'请输入管理员备注！');
		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}
	
</script>
	</head>
<body>
	<form action="goodsinfo/${msg }.do" name="Form" id="Form" method="post">
		<input type="hidden" name="ID" id="ID" value="${pd.ID}"/>
		<div id="zhongxin">
		<table id="table_report" class="table table-striped table-bordered table-hover">
			<tr>
				<td class="edit_td">商品名称:</td>
				<td><input type="text" name="NAME" id="NAME" value="${pd.NAME}" maxlength="32" placeholder="这里输入商品名称" title="商品名称"/></td>
			</tr>
			<tr>
				<td class="edit_td">商品介绍:</td>
				<td><input type="text" name="INTRODUCE" id="INTRODUCE" value="${pd.INTRODUCE}" maxlength="32" placeholder="这里输入商品介绍" title="商品介绍"/></td>
			</tr>
			<tr>
				<td class="edit_td">商品描述:</td>
				<td><input type="text" name="DESC" id="DESC" value="${pd.DESC}" maxlength="32" placeholder="这里输入商品描述" title="商品描述"/></td>
			</tr>
			<tr>
				<td class="edit_td">商品原价:</td>
				<td><input type="text" name="PRICE" id="PRICE" value="${pd.PRICE}" maxlength="32" placeholder="这里输入商品原价" title="商品原价"/></td>
			</tr>
			<tr>
				<td class="edit_td">折扣价:</td>
				<td><input type="text" name="DISCOUNT_PRICE" id="DISCOUNT_PRICE" value="${pd.DISCOUNT_PRICE}" maxlength="32" placeholder="这里输入折扣价" title="折扣价"/></td>
			</tr>
			<tr>
				<td class="edit_td">销售量:</td>
				<td><input type="text" name="SALES_NUM" id="SALES_NUM" value="${pd.SALES_NUM}" maxlength="32" placeholder="这里输入销售量" title="销售量"/></td>
			</tr>
			<tr>
				<td class="edit_td">介绍图地址:</td>
				<td><input type="text" name="IMAGE_URL" id="IMAGE_URL" value="${pd.IMAGE_URL}" maxlength="32" placeholder="这里输入介绍图地址" title="介绍图地址"/></td>
			</tr>
			<tr>
				<td class="edit_td">bannel地址:</td>
				<td><input type="text" name="BANNEL_URL" id="BANNEL_URL" value="${pd.BANNEL_URL}" maxlength="32" placeholder="这里输入bannel地址" title="bannel地址"/></td>
			</tr>
			<tr>
				<td class="edit_td">商品类型:</td>
				<td><input type="text" name="TYPE" id="TYPE" value="${pd.TYPE}" maxlength="32" placeholder="这里输入商品类型" title="商品类型"/></td>
			</tr>
			<tr>
				<td class="edit_td">商品状态:</td>
				<td><input type="text" name="STATUS" id="STATUS" value="${pd.STATUS}" maxlength="32" placeholder="这里输入商品状态" title="商品状态"/></td>
			</tr>
			<tr>
				<td class="edit_td">管理员备注:</td>
				<td><input type="text" name="REMARK" id="REMARK" value="${pd.REMARK}" maxlength="32" placeholder="这里输入管理员备注" title="管理员备注"/></td>
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