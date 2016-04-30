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
	<base href="<%=basePath%>"><!-- jsp文件头和头部 -->
	<%@ include file="../system/admin/top.jsp"%>
	</head>
<body>
		
<div class="container-fluid" id="main-container">


<div id="page-content" class="clearfix">
						
  <div class="row-fluid">

	<div class="row-fluid">
	
			<!-- 检索  -->
			<form action="channelorder/list.do" method="post" name="Form" id="Form">
			<table>
				<tr>
					<td>
						<span class="input-icon">
							<input autocomplete="off" id="nav-search-input" type="user_name" name="user_name" value="${pd.user_name}" placeholder="用户姓名" />
                            <i id="nav-search-icon" class="icon-search"></i>
						</span>
					</td>
					<td>
						<span class="input-icon">
							<input autocomplete="off" id="nav-search-input" type="order_no" name="order_no" value="${pd.order_no}" placeholder="订单号" />
                            <i id="nav-search-icon" class="icon-search"></i>
						</span>
					</td>
					<td><input class="span10 date-picker" name="startTime" id="startTime" value="${pd.startTime}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="开始日期"/></td>
					<td><input class="span10 date-picker" name="endTime" id="endTime" value="${pd.endTime}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="结束日期"/></td>
                    <td style="vertical-align:top;">

                        <select class="chzn-select" name="status" id="status" data-placeholder="请选择" style="vertical-align:top;width: 120px;">
                            <option value="">全部</option>
                            <option <c:if test="${pd.status=='0'}">selected</c:if> value="0">申请中</option>
                            <option <c:if test="${pd.status==1}">selected</c:if> value="1">准备中</option>
                            <option <c:if test="${pd.status==2}">selected</c:if> value="2">申请通过</option>
							<option <c:if test="${pd.status==3}">selected</c:if> value="3">提现成功</option>
							<option <c:if test="${pd.status==4}">selected</c:if> value="4">交易完成</option>
							<option <c:if test="${pd.status==5}">selected</c:if> value="5">交易失败</option>
						</select>
                    </td>

					<td style="vertical-align:top;"><button class="btn btn-mini btn-light" onclick="search();"  title="检索"><i id="nav-search-icon" class="icon-search"></i></button></td>
				</tr>
			</table>
			<!-- 检索  -->
		
		
			<table id="table_report" class="table table-striped table-bordered table-hover">
				
				<thead>
					<tr>
						<th class="center">
						<label><input type="checkbox" id="zcheckbox" /><span class="lbl"></span></label>
						</th>
						<th class="center">序号</th>
						<th class="center">金额</th>
						<th class="center">银行卡ID</th>
						<th class="center">请求订单号</th>
						<th class="center">同步应答码</th>
						<th class="center">异步应答码</th>
						<th class="center">通知时间</th>
						<th class="center">外部订单号</th>
						<th class="center">用户ID</th>
						<th class="center">状态</th>
						<th class="center">创建时间</th>
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
									<label><input type='checkbox' name='ids' value="${var.ID}" /><span class="lbl"></span></label>
								</td>
								<td class='center' style="width: 30px;">${vs.index+1}</td>
										<td><fmt:formatNumber value="${var.AMOUNT / 100}" pattern="0.00"/></td>
										<td>${var.BANK_ID}</td>
										<td>${var.ORDER_NO}</td>
										<td>${var.SYN_RESULT}</td>
										<td>${var.ASY_RESULT}</td>
										<td>${var.NOTIFY_TIME}</td>
										<td>${var.TRAN_FLOW}</td>
										<td>${var.USER_ID}</td>
										<td>
                                            <!--   ChannelOrder. STATUS_CREATE=0;STATUS_SUCCESS=1;//金运通申请提现成功,
                                            STATUS_FAILED=2;STATUS_WITHDRAW=3;//金运通到账成功
                                            STATUS_AGREE=4;//管理员同意提现,金运通未返回申请结果 -->
    										<c:if test="${var.STATUS == 0 }">申请中</c:if>
    										<c:if test="${var.STATUS == 1 }">准备中</c:if>
                                            <c:if test="${var.STATUS == 2 }">申请通过</c:if>
                                            <c:if test="${var.STATUS == 3 }">提现成功</c:if>
                                            <c:if test="${var.STATUS == 4 }">交易完成</c:if>
                                            <c:if test="${var.STATUS == 5 }">交易失败</c:if>

                                        </td>
										<td><fmt:formatDate value="${var.CREATE_TIME}" pattern="yyyy-MM-dd HH:mm:ss"/> </td>
								<!--
								<td style="width: 30px;" class="center">
                                     修改，删除，应该不用操作
									<div class='hidden-phone visible-desktop btn-group'>
									
										<c:if test="${QX.edit != 1 && QX.del != 1 }">
										<span class="label label-large label-grey arrowed-in-right arrowed-in"><i class="icon-lock" title="无权限"></i></span>
										</c:if>
										<div class="inline position-relative">
										<button class="btn btn-mini btn-info" data-toggle="dropdown"><i class="icon-cog icon-only"></i></button>
										<ul class="dropdown-menu dropdown-icon-only dropdown-light pull-right dropdown-caret dropdown-close">
											<c:if test="${QX.edit == 1 }">
											<li><a style="cursor:pointer;" title="编辑" onclick="edit('${var.ID}');" class="tooltip-success" data-rel="tooltip" title="" data-placement="left"><span class="green"><i class="icon-edit"></i></span></a></li>
											</c:if>
											<c:if test="${QX.del == 1 }">
											<li><a style="cursor:pointer;" title="删除" onclick="del('${var.ID}');" class="tooltip-error" data-rel="tooltip" title="" data-placement="left"><span class="red"><i class="icon-trash"></i></span> </a></li>
											</c:if>
										</ul>
										</div>
									</div>

								</td>
								-->
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
			
		<div class="page-header position-relative">
		<table style="width:100%;">
			<tr>
				<td style="vertical-align:top;">
                    <c:if test="${QX.add == 1 }">
					    <a class="btn btn-small btn-success" onclick="readyAll('需要先汇总吗?');">准备</a>
					</c:if>
                    <c:if test="${QX.add == 1 }">
                        <a class="btn btn-small btn-success" onclick="makeAll('确定批准准备中吗?');">批准</a>
                    </c:if>
                    <!--
					<c:if test="${QX.del == 1 }">
					<a class="btn btn-small btn-danger" onclick="makeAll('确定要批准选中的申请吗?');" title="批量删除" ><i class='icon-trash'></i></a>
					</c:if>-->
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
		<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
		<script src="static/js/bootstrap.min.js"></script>
		<script src="static/js/ace-elements.min.js"></script>
		<script src="static/js/ace.min.js"></script>
		
		<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script><!-- 下拉框 -->
		<script type="text/javascript" src="static/js/bootstrap-datepicker.min.js"></script><!-- 日期框 -->
		<script type="text/javascript" src="static/js/bootbox.min.js"></script><!-- 确认窗口 -->
		<!-- 引入 -->
		<script type="text/javascript" src="static/js/jquery.tips.js"></script><!--提示框-->
		<script type="text/javascript">
		
		$(top.hangge());
		
		//检索
		function search(){
			top.jzts();
			$("#Form").submit();
		}
		
		//新增
		function add(){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="新增";
			 diag.URL = '<%=basePath%>channelorder/goAdd.do';
			 diag.Width = 450;
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
		function del(Id){
			bootbox.confirm("确定要删除吗?", function(result) {
				if(result) {
					top.jzts();
					var url = "<%=basePath%>channelorder/delete.do?ID="+Id+"&tm="+new Date().getTime();
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
			 diag.URL = '<%=basePath%>channelorder/goEdit.do?ID='+Id;
			 diag.Width = 450;
			 diag.Height = 355;
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 nextPage(${page.currentPage});
				}
				diag.close();
			 };
			 diag.show();
		}
		</script>
		
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
			
		});
		
		
		//批量操作之批量批准
		function makeAll(msg){
			bootbox.confirm(msg, function(result) {
				if(result) {
					var str = '';
					for(var i=0;i < document.getElementsByName('ids').length;i++)
					{
						  if(document.getElementsByName('ids')[i].checked){
						  	if(str=='') str += document.getElementsByName('ids')[i].value;
						  	else str += ',' + document.getElementsByName('ids')[i].value;
						  }
					}
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
						if(msg == '确定执行批准吗?'){
							//top.jzts();
							$.ajax({
								type: "POST",
								url: '<%=basePath%>channelorder/passAll.do?tm='+new Date().getTime(),
						    	data: {DATA_IDS:str},
								dataType:'json',
								//beforeSend: validateData,
								cache: false,
								success: function(data){
									 $.each(data.list, function(i, list){
										 top.jzts();
										 setTimeout("self.location=self.location",100);
									 });
								}
							});
						}
					}
				}
			});
		}

		//批量准备
		function readyAll(msg){
			bootbox.confirm(msg, function(result) {
				if(result) {
					var str = '';
					for(var i=0;i < document.getElementsByName('ids').length;i++)
					{
						if(document.getElementsByName('ids')[i].checked){
							if(str=='') str += document.getElementsByName('ids')[i].value;
							else str += ',' + document.getElementsByName('ids')[i].value;
						}
					}
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
						if(msg == '需要先汇总吗?'){
							//top.jzts();
							$.ajax({
								type: "POST",
								url: '<%=basePath%>channelorder/sumAll.do?tm='+new Date().getTime(),
								data: {DATA_IDS:str},
								dataType:'json',
								//beforeSend: validateData,
								cache: false,
								success: function(data){
									$.each(data.list, function(i, list){
										//nextPage(${page.currentPage});
										var result = data.list[i];
										if(result.msg == "1") {
											bootbox.alert("汇总金额是："+result.rs+"元", function() {
												batchAll(str);
											});
										} else if(result.msg == "2"){
											bootbox.alert("选中项的状态必须为申请中!");
										} else if(result.msg == "3") {
											bootbox.alert("汇总失败！");
										} else {
											bootbox.alert("操作有误！");
										}
									});

								}
							});
						} else {
							alert("ddd");
							batchAll(str);
						}
					}
				}
			});
		}

		function batchAll(str) {
			bootbox.confirm("确认将申请中状态改为准备中吗?", function() {
				//top.jzts();
				$.ajax({
					type: "POST",
					url: '<%=basePath%>channelorder/readyAll.do?tm='+new Date().getTime(),
					data: {DATA_IDS:str},
					dataType:'json',
					//beforeSend: validateData,
					cache: false,
					success: function(data){
						$.each(data.list, function(i, list){
							top.jzts();
							setTimeout("self.location=self.location",100);
						});
					}
				});
			})
		}

		//导出excel
		function toExcel(){
			window.location.href='<%=basePath%>channelorder/excel.do';
		}
		</script>
		
	</body>
</html>

