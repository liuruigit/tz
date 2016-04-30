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
    <span style="background-color: aqua;">登录</span>
    <form action="${pageContext.request.contextPath}/wxBinding/binding" method="post">
        <input type="hidden" name="openId" value="${openId}"/>
        用户名：<input name="accName" type="text"><br/>
        密码：<input name="pwd" type="text"/><br/>
        IMEI<input name="imei" type="imei"/><br/>
        <button type="submit">submit</button>
    </form>
</div>
</body>
</html>
