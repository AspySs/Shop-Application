<html>
<head>
    <title>Charges</title>
</head>
<body>
<#if charges?has_content>
    <ul>
        <#list charges as charge>
            <li>${charge.getId()} ${charge.getAmount()} ${charge.getChargeDate()} ${charge.getExpanseItem().getId()} ${charge.getExpanseItem().getName()}</li>
        </#list>
    </ul>
<#else>
    <p>Charges not found</p>
</#if>
</body>
</html>