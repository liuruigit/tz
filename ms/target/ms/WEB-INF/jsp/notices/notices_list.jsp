<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getLocalPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
	<head>
	<base href="<%=basePath%>"><!-- jsp文件头和头部 -->
	<%@ include file="../system/admin/top.jsp"%>
	</head>
<body>
		
<div class="container-fluid" id="main-container">


<div id="page-content" class="clearfix">
						
  <div class="row-fluid">

	<div class="row-fluid">
	
			<!-- 检索  -->
			<form action="notices/list.do" method="post" name="Form" id="Form">
			<table>
				<tr>
					<td>
						<span class="input-icon">
							<input autocomplete="off" id="nav-search-input" type="TITLE" name="TITLE" value="" placeholder="ID\标题" />
							<i id="nav-search-icon" class="icon-search"></i>
						</span>
					</td>

					<td style="vertical-align:top;"><button class="btn btn-mini btn-light" onclick="search();"  title="检索"><i id="nav-search-icon" class="icon-search"></i></button></td>
					<%--<c:if test="${QX.cha == 1 }">
					<td style="vertical-align:top;"><a class="btn btn-mini btn-light" onclick="toExcel();" title="导出到EXCEL"><i id="nav-search-icon" class="icon-download-alt"></i></a></td>
					</c:if>--%>
				</tr>
			</table>
			<!-- 检索  -->
		
		
			<table id="table_report" class="table table-striped table-bordered table-hover" >
				
				<thead>
					<tr>
						<th class="center">
						<label><input type="checkbox" id="zcheckbox" /><span class="lbl"></span></label>
						</th>
						<th class="center">序号</th>
                        <th class="center">ID</th>
						<th class="center">标题</th>
						<th class="center">摘要</th>
                        <th class="center">分类</th>
                        <th class="center">排序</th>
                        <th class="center">图片</th>
						<th class="center">创建日期</th>
						<th class="center">操作</th>
					</tr>
				</thead>
										
				<tbody>
					
				<!-- 开始循环 -->	
				<c:choose>
					<c:when test="${not empty varList}">
						<c:if test="${QX.cha == 1 }">
						<c:forEach items="${varList}" var="var" varStatus="vs">
							<tr>
								<td class='center' style="width: 30px;">
									<label><input type='checkbox' name='ids' value="${var.NOTICES_ID}" /><span class="lbl"></span></label>
								</td>
								<td class='center' style="width: 30px;">${vs.index+1}</td>
                                <td>${var.NOTICES_ID}</td>
                                <td >${var.TITLE}</td>
                                <td onmouseover="showBody(this, '${var.SUMMARY}')" onmouseout="hideBody(this)">
									<c:choose>
										<c:when test="${fn:length(var.SUMMARY) > 10}">
											${fn:substring(var.SUMMARY, 0, 10)}...
										</c:when>
										<c:otherwise>
											${var.SUMMARY}
										</c:otherwise>
									</c:choose>

								</td>
                                <!--
                                <td style="white-space: nowrap;overflow: hidden;text-overflow:ellipsis;width:40%;">
                                    ${var.CONTENT}
                                </td>-->


                                <td>
                                    <c:forEach items="${contentTypes}" var="type">
                                        <c:if test="${type.TYPE_KEY == var.CONTENT_TYPE}">${type.TYPE_DES} </c:if>
                                    </c:forEach>
                                </td>
                                <td>
                                     <c:if test="${var.INDEX == 0}">不置顶 </c:if>
                                     <c:if test="${var.INDEX == 5}">置顶第一条 </c:if>
                                     <c:if test="${var.INDEX == 4}">置顶第二条 </c:if>
                                     <c:if test="${var.INDEX == 3}">置顶第三条 </c:if>
                                     <c:if test="${var.INDEX == 2}">置顶第四条 </c:if>
                                     <c:if test="${var.INDEX == 1}">置顶第五条 </c:if>
                                </td>
                                <td>
                                    <c:if test="${var.PICTURE_URL != null }">
                                        <span class="btn btn-info btn-small tooltip-info"
											  data-rel="popover"
                                              data-placement="bottom"
											  title=""
                                              data-content="<img src='${gateway}/${var.PICTURE_URL}' width='400px' />"
                                              data-original-title="图片">查看图片</span>
                                    </c:if>
                                </td>

                                <td><fmt:formatDate value="${var.CREATE_TIME}" pattern="yyyy-MM-dd HH:mm"/></td>
								<td style="width: 30px;" class="center">
									<div class='hidden-phone visible-desktop btn-group'>
									
										<c:if test="${QX.edit != 1 && QX.del != 1 }">
										<span class="label label-large label-grey arrowed-in-right arrowed-in"><i class="icon-lock" title="无权限"></i></span>
										</c:if>
										<div class="inline position-relative">

                                            <button class="btn btn-mini btn-info" data-toggle="dropdown"><i class="icon-cog icon-only"></i></button>
                                            <ul class="dropdown-menu dropdown-icon-only dropdown-light pull-right dropdown-caret dropdown-close">
                                                <c:if test="${QX.edit == 1 }">
                                                <li><a style="cursor:pointer;" title="编辑" onclick="edit('${var.NOTICES_ID}');" class="tooltip-success" data-rel="tooltip" title="" data-placement="left"><span class="green"><i class="icon-edit"></i></span></a></li>
                                                </c:if>
                                                <c:if test="${QX.del == 1 }">
                                                <li><a style="cursor:pointer;" title="删除" onclick="del('${var.NOTICES_ID   }');" class="tooltip-error" data-rel="tooltip" title="" data-placement="left"><span class="red"><i class="icon-trash"></i></span> </a></li>
                                                </c:if>

                                                <c:if test="${QX.add == 1 }">
                                                    <li><a style="cursor:pointer;" title="预览" onclick="preview('${var.NOTICES_ID}');" class="tooltip-success" data-rel="tooltip" title="" data-placement="left"><span class="green"><i class="icon-flag"></i></span></a></li>
                                                    <li><a style="cursor:pointer;" title="生成" onclick="generateHtml('${var.NOTICES_ID}');" class="tooltip-success" data-rel="tooltip" title="" data-placement="left"><span class="green"><i class="icon-ok"></i></span></a></li>
                                                </c:if>
                                            </ul>

										</div>
									</div>
								</td>
							</tr>
						
						</c:forEach>
						</c:if>
						<c:if test="${QX.cha == 0 }">
							<tr>
								<td colspan="100" class="center">您无权查看</td>
							</tr>
						</c:if>
					</c:when>
					<c:otherwise>
						<tr class="main_info">
							<td colspan="100" class="center" >没有相关数据</td>
						</tr>
					</c:otherwise>
				</c:choose>
					
				
				</tbody>
			</table>

		<!--隐藏的div,用于显示摘要-->
		<div id="showSum" style="display: none;position: absolute; float: left; z-index: 1000px;border: 1px solid gainsboro; background-color: #FFFFCC; padding: 5px;">
		</div>
		<div class="page-header position-relative">
		<table style="width:100%;">
			<tr>
				<td style="vertical-align:top;">
					<c:if test="${QX.add == 1 }">
					<a class="btn btn-small btn-success" onclick="add();">新增</a>
					</c:if>
					<%--<c:if test="${QX.del == 1 }">--%>
					<%--<a class="btn btn-small btn-danger" onclick="makeAll('确定要删除选中的数据吗?');" title="批量删除" ><i class='icon-trash'></i></a>--%>
					<%--</c:if>--%>
				</td>
				<td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
			</tr>
		</table>
		</div>
		</form>
	</div>
 
 
 
 
	<!-- PAGE CONTENT ENDS HERE -->
  </div><!--/row-->
	
</div><!--/#page-content-->
</div><!--/.fluid-container#main-container-->
		
		<!-- 返回顶部  -->
		<a href="#" id="btn-scroll-up" class="btn btn-small btn-inverse">
			<i class="icon-double-angle-up icon-only"></i>
		</a>
		
		<!-- 引入 -->
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
		
		$(top.hangge() );
		
		//检索
		function search(){
			top.jzts();
			$("#Form").submit();
		}

	/*	//图片隐藏
		function showImg(obj) {
			$(".imgbutton").each(function() {
				alert("fds");
				alert($(this).attr("data-rel"));
			});
		}*/

		//显示摘要
		function showBody(obj, summary) {
			if(summary.length > 0) {
				var offset = $(obj).offset();
				var t = offset.top;
				var l = offset.left;
				var w = $(obj).width();
				$("#showSum").css("top", t+5);
				$("#showSum").css("left", l+w+10);
				$("#showSum").html(summary);
				$("#showSum").show();
			}

		}

		function hideBody(obj) {
			$("#showSum").hide();
		}

		//新增
		function add(){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="新增";
			 diag.URL = '<%=basePath%>notices/goAdd.do';
			 diag.Width = 800;
			 diag.Height = 600;
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
                     console.log('curruentPage = ' + ${page.currentPage});
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
		function del(Id){
			bootbox.confirm("确定要删除吗?", function(result) {
				if(result) {
					top.jzts();
					var url = "<%=basePath%>notices/delete.do?ID="+Id+"&tm="+new Date().getTime();
					$.get(url,function(data){
						nextPage(${page.currentPage});
					});
				}
			});
		}
		
		//修改
		function edit(Id){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="编辑";
			 diag.URL = '<%=basePath%>notices/goEdit.do?ID='+Id;
             diag.Width = 800;
             diag.Height = 600;
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 nextPage(${page.currentPage});
				}
				diag.close();
			 };
			 diag.show();
		}

        //预览
        function preview( Id ){
            var URL = '<%=basePath%>notices/goPreview.do?ID='+Id;
            window.open(URL);
        }

        //生成html
        function generateHtml( Id ){
            bootbox.confirm("确定生成页面?", function(result) {

                if(result) {
                    var url = '<%=basePath%>notices/generateHtml.do';

                    $.post(url , { 'url' :"${pd.local_ip}notices/goPreview.do?ID=" +Id , 'ID' : Id } , function(data){
                        if( 'ok' == data.result){
                            bootbox.alert('生成成功!');
                        }else{
                            bootbox.alert('生成失败!请检查');
                        }
                    });
                }
            });
        }
		</script>`
		
		<script type="text/javascript">

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

            $('[data-rel=popover]').popover({html:true});
        });
		
		
		//批量操作
		function makeAll(msg){
			bootbox.confirm(msg, function(result) {
				if(result) {
					var str = new Array();
					$("input[name='ids']:checked").each(function() {
						str.push($(this).val());
					});
					alert(str);
					if(str==''){
						bootbox.dialog("您没有选择任何内容!", 
							[
							  {
								"label" : "关闭",
								"class" : "btn-small btn-success",
								"callback": function() {
									//Example.show("great success");
									}
								}
							 ]
						);
						
						$("#zcheckbox").tips({
							side:3,
				            msg:'点这里全选',
				            bg:'#AE81FF',
				            time:8
				        });
						
						return;
					}else{
						if(msg == '确定要删除选中的数据吗?'){
							top.jzts();
							$.ajax({
								type: "POST",
								url: '<%=basePath%>notices/deleteAll.do?tm='+new Date().getTime(),
						    	data: {DATA_IDS:str.join(",")},
								dataType:'json',
								//beforeSend: validateData,
								cache: false,
								success: function(data){
									 $.each(data.list, function(i, list){
											nextPage(${page.currentPage});
									 });
								}
							});
						}
					}
				}
			});
		}
		
		//导出excel
		function toExcel(){
			window.location.href='<%=basePath%>notices/excel.do';
		}
		</script>
		
	</body>
</html>

