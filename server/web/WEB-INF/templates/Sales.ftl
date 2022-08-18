<html>
<head>
    <title>Sales</title>
</head>
<body>
<#if sales?has_content>
    <ul>
        <#list sales as sale>
            <li>${sale.getId()} ${sale.getQuantity()} ${sale.getAmount()} ${sale.getSaleDate()} ${sale.getWarehouse().getId()}</li>
        </#list>
    </ul>
<#else>
    <p>Sales not found</p>
</#if>
</body>
</html>