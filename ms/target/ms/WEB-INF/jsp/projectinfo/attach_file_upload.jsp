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
	<link rel="stylesheet" href="static/css/style.css" media="screen" type="text/css">
</head>

<body>
<form action="projectinfo/saveAttach.do" name="Form" id="Form" method="post">
	<input type="hidden" name="ID" id="ID" value="${pd.ID}"/>
	<input type="hidden" name="PRO_ID" id="PRO_ID" value="${pd.PRO_ID}"/>
	<input type="hidden" name="FILE_PATH" id="FILE_PATH" value="${pd.FILE_PATH}"/>
	<div id="zhongxin">
		<table id="table_report" class="table table-striped table-bordered table-hover">
			<tr>
				<td class="edit_td">附件名称:</td>
				<td><input type="text" onblur="check_FileName(this)" name="NAME" id="NAME" value="${pd.NAME}" maxlength="30" placeholder="附件名称" title="附件名称"/></td>
			</tr>
			<tr>
				<td class="edit_td">附件描述:</td>
				<td><input type="text" name="DESC" id="DESC" value="${pd.DESC}" maxlength="32" placeholder="附件描述" title="附件描述"/></td>
			</tr>
			<tr>
				<td class="edit_td">附件:</td>
				<td>
					<a href="javascript:" class="btn btn-small fileUpload" title="上传附件">上传附件</a>&nbsp;&nbsp;
					<a href="javascript:" class="btn-small" id="btn">删除</a>
					<ul id="IMGLIST">
						<%--<li><img src="images/11287113923_57d37ed9d3_q.jpg" /></li>
						<li><img src="images/8095683964_9e27753908_q.jpg" /></li>
						<li><img src="images/8018956825_67bf62c098_q.jpg" /></li>
						<li><img src="images/7587724752_cdb9f0c444_q.jpg" /></li>
						<li><img src="images/7587738254_707a32f27b_q.jpg" /></li>
						<li><img src="images/8095680852_893f685cbd_q.jpg" /></li>
						<li><img src="images/8018953043_c6ef9e3b29_q.jpg" /></li>
						<li><img src="images/7445019824_914dea4ac3_q.jpg" /></li>--%>
					</ul>
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
<script type="text/javascript" src="<%=basePath%>static/js/index.js"></script>

<script type="text/javascript">

	function check_FileName(obj) {
		var fileName = $(obj).val();
		var proId = $("#PRO_ID").val();
		if(fileName == null || fileName == "") {
			$(obj).tips({
				side:3,
				msg:'附件名称不能为空!',
				bg:'#AE81FF',
				time:2
			});
			$(obj).val('');
			$(obj).focus();
			return false;
		} else {
			$.post("<%=basePath%>projectinfo/checkSaveFile.do", {"time":new Date(), "PRO_ID":proId, "NAME":fileName}, function(result) {
				if(result.code >= 1){
					$(obj).tips({
						side:3,
						msg:'该资产信息存在同名附件，请重新命名!',
						bg:'#AE81FF',
						time:2
					});
					$(obj).val('');
					$(obj).focus();
				}
			});
		}
	}

	//删除
	$('#btn').click(function(){
		$('li.selected').each(function(){
			$(this).remove();
		});
	});

	function save(){
		check_blank("#NAME",'请输入项目名称！');
		check_blank("#DESC",'请输入资产类型！');
		check_blank("#TYPE",'请输入地域！');
		var ID = $('#ID').val();
		var PRO_ID = $('#PRO_ID').val();
		var NAME = $('#NAME').val();
		var DESC = $('#DESC').val();
		//获取filePath的值
		var FILE_PATH = "";
		$("li").each(function() {
			var filepath = $(this).find("img").attr("filepath");
			FILE_PATH = FILE_PATH + filepath;
		});
		if(FILE_PATH == null || FILE_PATH == "") {
			alert("请上传附件图片！");
			return false;
		}
		var JSONDATA = {
			"ID":ID,
			"PRO_ID":PRO_ID,
			"FILE_PATH":FILE_PATH,
			"NAME":NAME,
			"DESC":DESC,
		};
		$.post($("#Form").attr("action"), JSONDATA, function(result) {
			if(result == 'success') {
				top.Dialog.close();
			}
		})
	}


	function log(obj1, obj2){
		if ('console' in window) {
			obj2 ? console.log(obj1, obj2) : console.log(obj1);
		}
	}
	$(function(){
		$(top.hangge());



		//保存
		$('.fileUpload').click(function(){
			var projectId = $('#PRO_ID').val();
			var fileType = $('#NAME').val();
			var diag = new top.Dialog();
			var desc = $.trim($("#DESC").val());
			if(fileType == null || fileType == ''){
				$("#NAME").tips({
					side:3,
					msg:'请输入附件名称后，再上传附件',
					bg:'#AE81FF',
					time:2
				});
				$("#NAME").focus();
				return false;
			} else if(desc == null || desc == ''){
				$("#DESC").tips({
					side:3,
					msg:'请输入附件描述后，再上传附件',
					bg:'#AE81FF',
					time:2
				});
				$("#DESC").focus();
				return false;
			} else {
				diag.Drag=true;
				diag.Title ="上传附件";
				diag.URL = '<%=basePath%>file/goUpload.do?fileType='+encodeURIComponent(fileType)+"&fileBelong="+projectId;
				diag.Width = 600;
				diag.Height = 500;
				diag.CancelEvent = function(){ //关闭事件
					var result = diag.innerFrame.contentWindow.document.getElementById('fileUrl').value;
					//var paths = diag.innerFrame.contentWindow.document.getElementById('filePath').value;
					$('#FILE_PATH').val(result);
					var array = result.split(",");
					//var pathArr = paths.split(",");
					for(var i = 0; i < array.length; i++) {
						if(i != 0) {
							$("#IMGLIST").append("<li><img filepath = ',"+ array[i] +"' src='<%=basePath%>uploadFiles/file/images/"+array[i]+"'/></li>");
						}
					}
					//刷新指定的js文件
					$.getScript('<%=basePath%>static/js/index.js',function(){
						//newFun('"Checking new script"');
					});
					diag.close();
				};
				diag.show();
			}
		});

	});
</script>

</body>
</html>
