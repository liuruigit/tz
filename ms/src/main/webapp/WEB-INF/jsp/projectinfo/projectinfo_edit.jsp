<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getLocalPort()+path+"/";
%>
<!doctype html>
<html>
<head>
	<base href="<%=basePath%>">
	<meta charset="utf-8" />
	<title></title>
	<meta name="description" content="overview & stats"/>
	<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
	<link href="static/css/bootstrap.min.css" rel="stylesheet" />
	<link href="static/css/bootstrap-responsive.min.css" rel="stylesheet"/>
	<link rel="stylesheet" href="static/css/font-awesome.min.css"/>
	<link rel="stylesheet" href="static/css/chosen.css" />
	<link rel="stylesheet" href="static/css/ace.min.css" />
	<link rel="stylesheet" href="static/css/ace-responsive.min.css"/>
	<link rel="stylesheet" href="static/css/ace-skins.min.css" />
	<link rel="stylesheet" href="static/css/jquery.uploader.css">
	<link rel="stylesheet" href="static/css/style.css" media="screen" type="text/css">
</head>

<body>
<form action="projectinfo/${msg }.do" name="Form" id="Form" method="post">
	<input type="hidden" name="ID" id="ID" value="${pd.ID}"/>
	<input type="hidden" name="PRO_ID" id="PRO_ID" value="${pd.PRO_ID}"/>
	<div id="zhongxin">
		<table id="table_report" class="table table-striped table-bordered table-hover">
			<tr>
				<td class="edit_td">融资标的NO:</td>
				<td><input type="text" onblur="checkNo()" name="PRO_NO" id="PRO_NO" value="${pd.PRO_NO}" maxlength="30" placeholder="融资标的NO" title="融资标的NO"/></td>
			</tr>
			<tr>
				<td class="edit_td">项目名称:</td>
				<td><input type="text" name="NAME" id="NAME" value="${pd.NAME}" maxlength="32" placeholder="这里输入项目名称" title="项目名称"/></td>
			</tr>
			<tr>
				<td class="edit_td">资产类型:</td>
				<td>
					<%--<input type="text" name="PROPERTY_TYPE" id="PROPERTY_TYPE" value="${pd.PROPERTY_TYPE}" maxlength="32" placeholder="这里输入资产类型" title="资产类型"/>--%>
					<select name="PROPERTY_TYPE" id="PROPERTY_TYPE" title="资产类型">
						<option <c:if test="${pd.PROPERTY_TYPE == 0}">selected</c:if> value="0">住宅</option>
						<option <c:if test="${pd.PROPERTY_TYPE == 1}">selected</c:if> value="1">商铺</option>
						<option <c:if test="${pd.PROPERTY_TYPE == 2}">selected</c:if> value="2">写字楼</option>
					</select>
				</td>
			</tr>
			<tr>
				<td class="edit_td">地域:</td>
				<td><input type="text" name="LOCATION" id="LOCATION" value="${pd.LOCATION}" maxlength="32" placeholder="这里输入地域" title="地域"/></td>
			</tr>
			<tr>
				<td class="edit_td">产权面积:</td>
				<td><input type="text" name="AREA" id="AREA" value="${pd.AREA}" maxlength="32" placeholder="这里输入产权面积" title="产权面积"/></td>
			</tr>
			<tr>
				<td class="edit_td">市场价:</td>
				<td><input type="text" name="MARKET_PRICE" id="MARKET_PRICE" value="${pd.MARKET_PRICE}" maxlength="32" placeholder="这里输入市场价" title="市场价"/></td>
			</tr>
			<%--<tr>
				<td class="edit_td">产权证:</td>
				<td>
					<select name="PROPERTY_CERT" id="PROPERTY_CERT" data-placeholder="产权证" class="select_top">
						<option <c:if test="${pd.PROPERTY_CERT==0}">selected</c:if> value="0">已办理</option>
						<option <c:if test="${pd.PROPERTY_CERT==1}">selected</c:if> value="1">可办理而未办理</option>
						<option <c:if test="${pd.PROPERTY_CERT==2}">selected</c:if> value="2">已办理网签备案</option>
						<option <c:if test="${pd.PROPERTY_CERT==3}">selected</c:if> value="3">可办理而未到办理时间</option>
						<option <c:if test="${pd.PROPERTY_CERT==4}">selected</c:if> value="4">短期内不可办理</option>

					</select>
				</td>
			</tr>
			<tr>
				<td class="edit_td">土地证:</td>
				<td>
					<select name="LAND_CERT" id="LAND_CERT" data-placeholder="土地证" class="select_top">
						<option <c:if test="${pd.LAND_CERT==0}">selected</c:if> value="0">已办理</option>
						<option <c:if test="${pd.LAND_CERT==1}">selected</c:if> value="1">可办理而未办理</option>
						<option <c:if test="${pd.LAND_CERT==2}">selected</c:if> value="2">短期内不可办理</option>
					</select>
				</td>
			</tr>--%>
			<tr>
				<td class="edit_td">资产权属:</td>
				<td>
					<input type="text" name="PROPERTY_OWNER" id="PROPERTY_OWNER" value="${pd.PROPERTY_OWNER}" maxlength="32" placeholder="这里输入资产权属" title="资产权属"/>
				</td>
			</tr>
			<tr>
				<td class="edit_td">详细信息:</td>
				<td>
					<textarea name="EXTRA_INFO" id="EXTRA_INFO" value="${pd.EXTRA_INFO}"
							  placeholder="这里输入详细信息" title="详细信息"></textarea>
				</td>
			</tr>
			<tr>
				<td class="edit_td">物权情况:</td>
				<td><input type="text" name="PROPERTY_RIGHT" id="PROPERTY_RIGHT" value="${pd.PROPERTY_RIGHT}" maxlength="32" placeholder="这里输入物权情况" title="物权情况"/></td>
			</tr>
			<tr>
				<td class="edit_td">附件详情:</td>
				<td><a href="javascript:" class="btn-small" id="btn">附件详情>></a>
				</td>
			</tr>
			<tr>
				<td style="text-align: center;" colspan="10">
					<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
					<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
					<a class="btn btn-mini btn-primary" onclick="attach_file();">添加附件</a>
				</td>
			</tr>
		</table>
	</div>

	<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>
</form>

<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
<script src="static/js/bootstrap.min.js"></script>
<script src="static/js/ace-elements.min.js"></script>
<script src="static/js/ace.min.js"></script>
<script type="text/javascript" src="static/js/jquery.tips.js"></script>
<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script><!-- 下拉框 -->
<script type="text/javascript" src="static/js/bootstrap-datepicker.min.js"></script><!-- 日期框 -->
<script type="text/javascript" src="static/js/myjs/validate.js"></script>
<!--script type="text/javascript" src="../static/js/jquery/jquery-1.7.2.min.js"></script-->
<script type="text/javascript" src="<%=basePath%>static/js/jquery.uploader.js"></script>

<script type="text/javascript">
	$("#btn").click(function() {
		 var pro_id = $("#PRO_ID").val();
		 if(pro_id == "" || pro_id == null) {
			 $("#btn").tips({
				 side:3,
				 msg:'请先输入有效融资标NO！',
				 bg:'#AE81FF',
				 time:2
			 });
			 $('#btn').val('');
			 $("#btn").focus();
		 	return false;
		 }
		 var diag = new top.Dialog();
		 diag.Drag=true;
		 diag.Title ="附件列表";
		 diag.URL = '${pageContext.request.contextPath}/projectinfo/findAttach.do?PRO_ID='+pro_id;
		 diag.Width = 550;
		 diag.Height = 355;
		 diag.CancelEvent = function(){ //关闭事件
		 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
			 if('${page.currentPage}' == '0'){
				top.jzts();
				setTimeout("self.location=self.location",100);
			 }else{
				 nextPage(${page.currentPage});
				 }
			 }
		 diag.close();
	 	};
	 diag.show();
	});

	function checkNo(){
		var val = $('#PRO_NO').val();
		$.get('<%=basePath%>project/findByNo.do?NO='+val,function(data){
			if(data.code == 2){
				$('#PRO_ID').val(data.obj.ID);
			}else if(data.code == 1){
				$("#PRO_NO").tips({
					side:3,
					msg:'该融资标已录入房产信息',
					bg:'#AE81FF',
					time:2
				});
				$('#PRO_NO').val('');
				$("#PRO_NO").focus();
			} else {
				$("#PRO_NO").tips({
					side:3,
					msg:'请输入有效的融资标的NO',
					bg:'#AE81FF',
					time:2
				});
				$('#PRO_NO').val('');
				$("#PRO_NO").focus();
			}
		});
	}

	//附件列表
	function attach_file_list() {
		var pro_id = $("#PRO_ID").val();
		if(pro_id == "" || pro_id == null) {
			return false;
		}
		var diag = new top.Dialog();
		diag.Drag=true;
		diag.Title ="附件列表";
		diag.URL = '${pageContext.request.contextPath}/projectinfo/findAttach.do?PRO_ID='+pro_id;
		diag.Width = 550;
		diag.Height = 355;
		diag.CancelEvent = function(){ //关闭事件
			if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
				if('${page.currentPage}' == '0'){
					top.jzts();
					setTimeout("self.location=self.location",100);
				}else{
					nextPage(${page.currentPage});
				}
			}
			diag.close();
		};
		diag.show();
	}

	//删除
	function attach_file(){
		var pro_id = $('#PRO_ID').val();
		var pro_no = $('#PRO_NO').val();
		if(pro_no == null || pro_no == ''){
			$("#PRO_NO").tips({
				side:3,
				msg:'请输入融资标的NO之后，再上传附件',
				bg:'#AE81FF',
				time:2
			});
			$("#PRO_NO").focus();
			return false;
		}
		var diag = new top.Dialog();
		diag.Drag=true;
		diag.Title ="新增";
		diag.URL = '<%=basePath%>projectinfo/goAttachFile.do?PRO_ID='+pro_id;
		diag.Width = 550;
		diag.Height = 355;
		diag.CancelEvent = function(){ //关闭事件
			if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
				if('${page.currentPage}' == '0'){
					top.jzts();
					setTimeout("self.location=self.location",100);
				}else{
					nextPage(${page.currentPage});
				}
			}
			diag.close();
		};
		diag.show();
	}

	function save(){
		if(!check_blank("#PRO_NO",'请输入融资标的NO！'))return;
		if(!check_blank("#NAME",'请输入项目名称！'))return;
		if(!check_str_len("项目名称", "#NAME", 30))return;
		if(!check_blank("#PROPERTY_TYPE",'请输入资产类型！'))return;
		if(!check_blank("#LOCATION",'请输入地域！'))return;
		if(!check_str_len("地域","#LOCATION",20))return;
		if(!check_blank("#AREA",'请输入产权的面积！'))return;
		if(!check_double("#AREA", '产权面积'))return;
		//if(!check_blank("#SELL_PRICE",'请输入出售价格！'))return;
		//if(!check_double("#SELL_PRICE", '出售价格'))return;
		//if(!check_blank("#MARKET_PRICE",'请输入市场价！'))return;
		if(!check_double("#MARKET_PRICE", '市场价'))return;
		if(!check_blank("#PROPERTY_RIGHT",'请输入物权情况！'))return;
		if(!check_str_len('物权情况', '#PROPERTY_RIGHT', 50))return;
		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}


	function log(obj1, obj2){
		if ('console' in window) {
			obj2 ? console.log(obj1, obj2) : console.log(obj1);
		}
	}
	$(function(){
		$(top.hangge());

	});
</script>



</body>
</html>
