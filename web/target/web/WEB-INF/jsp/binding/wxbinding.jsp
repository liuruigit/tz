<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/3/29
  Time: 22:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html >
<html>

<head>
    <meta name="Generator" content="ECSHOP v2.7.3" />
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width">
    <title>账号绑定 </title>
    <meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/public.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/flow.css">
    <style type="text/css">
        input{
            width:90%;
            height:25px;
            border:2px solid #B98834;
            padding:5px;
            margin:5px 0 10px 0;
            border-radius:5px;
            -webkit-border-radius:5px;
            -moz-border-radius:5px;
        }
    </style>

</head>

<body>
<div class="screen-wrap fullscreen login">
    <section id="cart-content">
        <form action="${pageContext.request.contextPath}${url}" method="post" id="form">
        <div class="qb_tac" style="padding:30px 0">

                <img src="${pageContext.request.contextPath}/img/icon.png" width="183px" height="89px">
                <input type="hidden" id="openId" name="openId" value="${openId}"/>
                <br><input  type="text" name="accName" id="accName" placeholder="请输入手机号码/用户名" />
                <br><input  type="password" name="pwd" id="pwd" placeholder="请输入登录密码" />
                <br><span style="color:#CB5966; font-size:13px;" id="errInfo"></span>

        </div>

        <div class="qb_gap" style="width:80%; margin:0 auto;">
            <span class="mod_btn" id="submitBtn" onclick="submitForm()">绑&nbsp;&nbsp;定</span>
        </div>
        </form>
    </section>
    <div style="height:72px;"></div>
    <section class="f_mask" style="display: none;"></section>
    <section class="f_block" id="choose" style="height:0px;"></section>
</div>
</div>
</div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
<script type="text/javascript">
    function submitForm() {
        var openId = $("#openId").val();
        var accName = $("#accName").val();
        var pwd = $("#pwd").val();

        if(openId == "" || openId.length < 1) {
            $("#errInfo").html("*绑定失败, 请重试！");
        } else if(!/^((13[0-9])|(15[^4,\D])|(18[0,5-9]))\d{8}$/.test(accName)){
            $("#errInfo").html("*用户名不合法！");
        } else if(!/(?!^[0-9]+$)(?!^[A-Z]+$)(?!^[a-z]+$)(?!^[^A-z0-9]+$)^.{6,16}/.test(pwd)) {
            $("#errInfo").html("*密码无效，请重新输入！");
        } else {
            var reqJSON = {"openId":openId, "accName":accName, "pwd":pwd};
            var url = $("#form").attr("action");
            $.post(url, reqJSON, function(data) {
                if(data.code == 0) {
                    WeixinJSBridge.call('closeWindow');
                } else {
                    $("#errInfo").html(data.message);
                }

            });
        }
    }
</script>
</body>

</html>

