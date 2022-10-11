<html>
<head>
    <title>Warehouses</title>
</head>
<body>
<#if warehouses?has_content>
    <ul>
        <#list warehouses as warehouse>
            <li>${warehouse.getId()} ${warehouse.getName()} ${warehouse.getQuantity()} ${warehouse.getAmount()} </li>
        </#list>
    </ul>
<#else>
    <p>warehouses not found</p>
</#if>
</body>
</html>