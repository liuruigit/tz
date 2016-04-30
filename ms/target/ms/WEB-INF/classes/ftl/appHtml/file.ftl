<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1, minimum-scale=1, maximum-scale=1">
    <style>img{max-width:320px !important;}</style>
    <title>${title}</title>
</head>
<body width=320px style="word-wrap:break-word; font-family:Arial">
    <#list images as img>
        <img src="../../images/${img}"/>
        <br/>
    </#list>
</body>
</html>