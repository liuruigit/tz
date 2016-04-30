<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getLocalPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
	<base href="<%=basePath%>">

	<!-- jsp文件头和头部 -->
	<%@ include file="../system/admin/top.jsp"%>

</head>
<body>

<div class="container-fluid" id="main-container">


	<div id="page-content" class="clearfix">
		<div class="page-header position-relative">
			<h1>
				对账报表 <small><i class="icon-double-angle-right"></i> </small>
			</h1>
		</div>
		<!--/page-header-->

		<div class="row-fluid">
			<!-- 检索  -->
			<form action="channelform/list.do" method="post" name="Form" id="Form">
				<table>
					<tr>
						<td>
						<td><input class="span10 date-picker" name="startDate" id="startDate" value="${pd.startDate}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="开始日期"/></td>
						<td><input class="span10 date-picker" name="endDate" id="endDate" value="${pd.endDate}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="结束日期"/></td>
						<td style="vertical-align:top;"><button class="btn btn-mini btn-light" onclick="search();"  title="检索"><i id="nav-search-icon" class="icon-search"></i></button></td>

					</tr>
				</table>
			</form>
			<div class="space-6"></div>
			<div class="row-fluid">
				<div id="chart1"></div><br/>
				<div id="chart2"></div><br/>
				<div id="chart3"></div><br/>
				<div id="chart4"></div><br/>
			</div>
		</div>

	</div>
	<!-- #main-content -->
</div>

<script type="text/javascript" src="<%=basePath%>static/js/jQuery.js"></script>
<script language="JavaScript" type="text/javascript" src="<%=basePath%>static/js/jqplot/jquery.jqplot.min.js "></script>
<script language="JavaScript" type="text/javascript" src="<%=basePath%>static/js/jqplot/examples/syntaxhighlighter/scripts/shCore.min.js "></script>
<script language="JavaScript" type="text/javascript" src="<%=basePath%>static/js/jqplot/examples/syntaxhighlighter/scripts/shBrushJScript.min.js "></script>
<script language="JavaScript" type="text/javascript" src="<%=basePath%>static/js/jqplot/examples/syntaxhighlighter/scripts/shBrushXml.min.js "></script>
<script language="JavaScript" type="text/javascript" src="<%=basePath%>static/js/jqplot/plugins/jqplot.logAxisRenderer.min.js "></script>
<script language="JavaScript" type="text/javascript" src="<%=basePath%>static/js/jqplot/plugins/jqplot.canvasTextRenderer.min.js "></script>
<script language="JavaScript" type="text/javascript" src="<%=basePath%>static/js/jqplot/plugins/jqplot.canvasAxisLabelRenderer.min.js "></script>
<script language="JavaScript" type="text/javascript" src="<%=basePath%>static/js/jqplot/plugins/jqplot.canvasAxisTickRenderer.min.js "></script>
<script language="JavaScript" type="text/javascript" src="<%=basePath%>static/js/jqplot/plugins/jqplot.dateAxisRenderer.min.js "></script>
<script language="JavaScript" type="text/javascript" src="<%=basePath%>static/js/jqplot/plugins/jqplot.categoryAxisRenderer.min.js "></script>
<script language="JavaScript" type="text/javascript" src="<%=basePath%>static/js/jqplot/plugins/jqplot.barRenderer.min.js "></script>"
<script language="JavaScript" type="text/javascript" src="<%=basePath%>static/js/jqplot/plugins/jqplot.pointLabels.min.js "></script>"
<script language="JavaScript" type="text/javascript" src="<%=basePath%>static/js/jqplot/plugins/jqplot.dateAxisRenderer.min.js "></script>
<script language="JavaScript" type="text/javascript" src="<%=basePath%>static/js/jqplot/plugins/jqplot.dateAxisRenderer.min.js "></script>
<link class= "include " rel= "stylesheet " type= "text/css " href= "<%=basePath%>static/js/jqplot/jquery.jqplot.min.css "/>
<link type= "text/css " rel= "stylesheet " href= "<%=basePath%>static/js/jqplot/examples/syntaxhighlighter/styles/shCoreDefault.min.css " />
<link type= "text/css " rel= "stylesheet " href= "<%=basePath%>static/js/jqplot/examples/syntaxhighlighter/styles/shThemejqPlot.min.css " />
<script language= "JavaScript " type= "text/javascript " src= "<%=basePath%>static/js/m_jqplot.js "></script>
<!-- basic scripts -->
<script type="text/javascript">window.jQuery || document.write("<script src='<%=basePath%>static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
<script src="<%=basePath%>static/js/bootstrap.min.js"></script>
<script src="<%=basePath%>static/js/ace-elements.min.js"></script>
<script src="<%=basePath%>static/js/ace.min.js"></script>

<script type="text/javascript" src="<%=basePath%>static/js/chosen.jquery.min.js"></script><!-- 下拉框 -->
<script type="text/javascript" src="<%=basePath%>static/js/bootstrap-datepicker.min.js"></script><!-- 日期框 -->
<script type="text/javascript" src="<%=basePath%>static/js/bootbox.min.js"></script><!-- 确认窗口 -->
<!-- 引入 -->
<script type="text/javascript" src="<%=basePath%>static/js/jquery.tips.js"></script><!--提示框-->
<script type="text/javascript">
	$(top.hangge());
	$(document).ready(function() {

		amountType2();
		countType2();
		amountType3();
		countType3();
		//加载次数报表
	});
	//检索
	function search(){
		top.jzts();
		$("#Form").submit();
	}

	function amountType2() {
		//加载充值金额报表
		var data = ${amountList2};
		var data_max = ${amountMax2}; //Y轴最大刻度
		var line_title = ${line_lable}; //曲线名称
		var y_label = "${amountY_lable2}"; //Y轴标题
		var x_label = "${x_lable}"; //X轴标题
		var x = ${x}; //定义X轴刻度值
		var title = "${titleAmount2}"; //统计图标标题
		//j.jqplot.diagram.base("chart1", data, line_title, "这是统计标题", x, x_label, y_label, data_max, 1);
		j.jqplot.diagram.base("chart1", data, line_title, title, x, x_label, y_label, data_max, 2);
	}

	function countType2() {
		//加载充值次数报表
		var data = ${countList2};
		var data_max = ${countMax2}; //Y轴最大刻度
		var line_title = ${line_lable}; //曲线名称
		var y_label = "${countY_lable2}"; //Y轴标题
		var x_label = "${x_lable}"; //X轴标题
		var x = ${x}; //定义X轴刻度值
		var title = "${titleCount2}"; //统计图标标题
		//j.jqplot.diagram.base("chart1", data, line_title, "这是统计标题", x, x_label, y_label, data_max, 1);
		j.jqplot.diagram.base("chart2", data, line_title, title, x, x_label, y_label, data_max, 2);
	}

	function amountType3() {
		//加载提现金额报表
		var data = ${amountList3};
		var data_max = ${amountMax3}; //Y轴最大刻度
		var line_title = ${line_lable}; //曲线名称
		var y_label = "${amountY_lable3}"; //Y轴标题
		var x_label = "${x_lable}"; //X轴标题
		var x = ${x}; //定义X轴刻度值
		var title = "${titleAmount3}"; //统计图标标题
		//j.jqplot.diagram.base("chart1", data, line_title, "这是统计标题", x, x_label, y_label, data_max, 1);
		j.jqplot.diagram.base("chart3", data, line_title, title, x, x_label, y_label, data_max, 2);
	}

	function countType3() {
		//加载提现报表
		var data = ${countList3};
		var data_max = ${countMax3}; //Y轴最大刻度
		var line_title = ${line_lable}; //曲线名称
		var y_label = "${countY_lable3}"; //Y轴标题
		var x_label = "${x_lable}"; //X轴标题
		var x = ${x}; //定义X轴刻度值
		var title = "${titleCount3}"; //统计图标标题
		//j.jqplot.diagram.base("chart1", data, line_title, "这是统计标题", x, x_label, y_label, data_max, 1);
		j.jqplot.diagram.base("chart4", data, line_title, title, x, x_label, y_label, data_max, 2);
	}


	$(function() {

		//下拉框
		$(".chzn-select").chosen();
		$(".chzn-select-deselect").chosen({allow_single_deselect:true});

		//日期框
		$('.date-picker').datepicker();

		//复选框
		$('table th input:checkbox').on('click' , function(){
			var that = this;
			$(this).closest('table').find('tr > td:first-child input:checkbox')
					.each(function(){
						this.checked = that.checked;
						$(this).closest('tr').toggleClass('selected');
					});

		});

	});
</script>
</body>
</html>
