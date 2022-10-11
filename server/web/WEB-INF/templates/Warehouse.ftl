<html>
<head>
    <meta charset="UTF-8">
    <title>Warehouse</title>
</head>
<body>
<#if warehouse?has_content>
<ul>
    <li>${warehouse.getId()}</li>
    <li>${warehouse.getName()}</li>
    <li>${warehouse.getQuantity()}</li>
    <li>${warehouse.getAmount()}</li>
</ul>
<#else>
    <p>warehouse not found</p>
</#if>
</body>
</html>