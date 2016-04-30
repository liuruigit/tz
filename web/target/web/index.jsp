<%--
  Created by IntelliJ IDEA.
  User: vic wu
  Date: 2015/8/14
  Time: 18:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>测试页面</title>
</head>
<body>
<div>
    <h3 style="color: #AA9234">转账接口测试</h3>
    <form action="/web/pay/jslwnUsjwr" method="post">
        转账参数：<textarea name="res" type="text" rows="20" cols="50"></textarea>
        MD5-KEY：<input name="key" type="text"/>
        <button type="submit">submit</button>
    </form>
</div>
<div style="background-color: #AA9234;height: 10px;"></div></br>
<div>
    <h3 style="color: #AA9234">登录</h3>
    <form action="/web/account/login" method="post">
        用户名：<input name="accName" type="text">
        密码：<input name="pwd" type="text"/>
        IMEI<input name="imei" type="imei"/>
        <button type="submit">submit</button>
    </form>
</div>
<div style="background-color: #AA9234;height: 10px;"></div></br>
<div>
    <h3 style="color: #AA9234">注册</h3>

    <form action="/web/account/add" method="post">
        用户名：<input name="accName" type="text">
        密码：<input name="pwd" type="text"/>
        IMEI<input name="imei" value="imeitest" type="text"/>
        邀请码<input name="recommend" type="text"/>
        <button type="submit">submit</button>
    </form>
</div>
<div style="background-color: #AA9234;height: 10px;"></div></br>
<div>
    <h3 style="color: #AA9234">校验交易密码</h3>
    <form action="/web/pwd/auth/checkTradePwd" method="post">
        token:<input type="text" name="token"  >
        imei:<input type="text" name="imei"  >
        交易密码<input name="tradePwd" type="text"/>
        <button type="submit">submit</button>
    </form>
</div>
<div style="background-color: #AA9234;height: 10px;"></div></br>
<div>
    <h3 style="color: #AA9234">解绑银行卡</h3>
    <form name="userForm1" action="/web/auth/pi/unbinding" enctype="multipart/form-data" method="post">
        <input type="file" name="file1">
        <input type="file" name="file2">
        <input type="file" name="file3">

        token:<input type="text" name="token"  >
        imei:<input type="text" name="imei"  >
        content:<input type="text" name="content"  >
        <input type="submit" value="上传" >
    </form>
</div>
<div style="border: 1px solid black;">
    <span style="background-color: aqua;">获取素材列表</span>
    <form action="${pageContext.request.contextPath}/wxBinding/getImageTextList" method="post">
        素材类型:<input type="text" name="type"  >
        偏移量:<input type="text" name="offset"  >
        数量：<input name="count" type="text"/>
        <button type="submit">submit</button>
    </form>
    <div style="border: 1px solid blue;">${msg}</div>
</div>
<br/>
<div style="border: 1px solid black;">
    <span style="background-color: aqua;">根据media_ID获取素材</span>
    <form action="${pageContext.request.contextPath}/wxBinding/getImageText" method="post">
        菜单value:<input type="text" name="keyName"/>
        素材ID:<input type="text" name="mediaId"  >
        <button type="submit">submit</button>
    </form>
</div>
</body>
</html>
