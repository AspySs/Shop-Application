<html>
<head>
    <meta charset="UTF-8">
    <title>ExpenseItems</title>
</head>
<body>
<#if exItem?has_content>
<ul>
    <li>${exItem.getId()}</li>
    <li>${exItem.getName()}</li>
</ul>
<#else>
    <p>exItem not found</p>
</#if>
</body>
</html>