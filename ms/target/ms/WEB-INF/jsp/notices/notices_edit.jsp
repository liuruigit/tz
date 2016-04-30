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
		if(!check_blank("#TITLE",'请输入标题！'))return;
		if(!check_str_len("标题","#TITLE", 50))return;
		if(!check_blank("#CONTENT",'请输入富文本内容！'))return;
		if(!check_blank("#PICTURE_URL",'请上传标题图片！'))return;
		if($("#SUMMARY").val().length > 54) {
			$("#SUMMARY").val("");
			tail("#SUMMARY","摘要的字符长度在54个以内");
			return false;
		}
		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}
	
</script>
	</head>
<body>
	<form action="notices/${msg }.do" name="Form" id="Form" method="post">
		<input type="hidden" name="ID" id="ID" value="${pd.NOTICES_ID}"/>
		<div id="zhongxin">
		<table id="table_report" class="table table-striped table-bordered table-hover">
			<tr>
				<td class="edit_td">标题:</td>
				<td><input type="text" name="TITLE" id="TITLE" value="${pd.TITLE}" maxlength="32" placeholder="这里输入标题" title="标题"/></td>
			</tr>
            <tr>
                <td class="edit_td">标题图片:</td>
                <td>

                    <input type="hidden" id="PICTURE_URL" name="PICTURE_URL" value="${pd.PICTURE_URL}"/>

                    <img src="${gateway}/${pd.PICTURE_URL}"  id="PICTURE_VIEW" style="display: none" width="400px"/>
                    <a href="javascript:" fileBelong="${notices_path}" class="btn btn-small fileUpload" fileType="NOTICES" title="上传">上传</a>
                </td>
            </tr>
			<tr>
				<td class="edit_td">摘要:</td>
				<td><textArea name="SUMMARY" id="SUMMARY" maxlength="64" placeholder="这里输入摘要" title="摘要" style="width: 400px;">${pd.SUMMARY}</textArea></td>
			</tr>
            <tr>
                <td class="edit_td">分类:</td>
                <td>
                    <select name="CONTENT_TYPE" id="CONTENT_TYPE">
                        <c:forEach items="${contentTypes}" var="var" varStatus="vs">
                            <option value ="${var.TYPE_KEY}">${var.TYPE_DES}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td class="edit_td">排序:</td>
                <td>
                    <select name="INDEX" id="INDEX">
                        <option value ="0" <c:if test="${pd.INDEX == 0}">selected </c:if> >不置顶</option>
                        <option value ="5" <c:if test="${pd.INDEX == 5}">selected </c:if> >置顶第一条</option>
                        <option value ="4" <c:if test="${pd.INDEX == 4}">selected </c:if> >置顶第二条</option>
                        <option value ="3" <c:if test="${pd.INDEX == 3}">selected </c:if> >置顶第三条</option>
                        <option value ="2" <c:if test="${pd.INDEX == 2}">selected </c:if> >置顶第四条</option>
                        <option value ="1" <c:if test="${pd.INDEX == 1}">selected </c:if> >置顶第五条</option>
                    </select>
                </td>
            </tr>

            <tr>
				<td class="edit_td">内容:</td>
				<td>
                    <script id="editor" name="CONTENT" type="text/plain" style="width:650px;height:259px;">${pd.CONTENT}</script>
                    <!--
                    <input type="text" name="CONTENT" id="CONTENT" value="" maxlength="32" placeholder="这里输入富文本内容" title="富文本内容"/></td>-->
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
    	<script type="text/javascript" src="static/js/myjs/validate.js"></script>

        <!-- 编辑框-->
        <script type="text/javascript" charset="utf-8">window.UEDITOR_HOME_URL = "<%=path%>/plugins/ueditor/";</script>
        <script type="text/javascript" charset="utf-8" src="plugins/ueditor/ueditor.config.js"></script>
        <script type="text/javascript" charset="utf-8" src="plugins/ueditor/ueditor.all.js"></script>
        <!-- 编辑框-->

		<script type="text/javascript">
		$(top.hangge());
		$(function() {
			
			//单选框
			$(".chzn-select").chosen(); 
			$(".chzn-select-deselect").chosen({allow_single_deselect:true}); 
			
			//日期框
			$('.date-picker').datepicker();

            //内容框
            var ue = UE.getEditor('editor');

            //判断ueditor 编辑器是否创建成功
            /*ue.addListener("ready", function () {
                // editor准备好之后才可以使用
                ue.setContent('${pd.CONTENT}');

            });*/
            //图片上传事件
            $('.fileUpload').click(function(){
                var fileType = $(this).attr('fileType');
                var fileBelong = $(this).attr('fileBelong');
                var diag = new top.Dialog();
                diag.Drag=true;
                diag.Title ="上传附件";
                diag.URL = '<%=basePath%>file/goUpload.do?fileType='+fileType+"&fileBelong="+encodeURIComponent(fileBelong);
                diag.Width = 600;
                diag.Height = 500;
                diag.CancelEvent = function(){ //关闭事件
                    var result = diag.innerFrame.contentWindow.document.getElementById('fileUrl').value;
                    var url = result.substring( "@".length ) ;
                    $('#'+'PICTURE_URL').val( url );
                    $('#'+'PICTURE_VIEW').attr('src', '${gateway}' + url );
                    $('#'+'PICTURE_VIEW').show();

                    diag.close();
                };
                diag.show();
            });

            <c:if test="${pd.PICTURE_URL != null }">
                $('#'+'PICTURE_VIEW').show();
            </c:if>


		});

		</script>
</body>
</html>