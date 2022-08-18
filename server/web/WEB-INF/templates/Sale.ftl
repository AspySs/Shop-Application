<html>
<head>
    <meta charset="UTF-8">
    <title>Sale</title>
</head>
<body>
<#if sale?has_content>
<ul>
    <li>${sale.getId()}</li>
    <li>${sale.getQuantity()}</li>
    <li>${sale.getAmount()}</li>
    <li>${sale.getSaleDate()}</li>
    <li>${sale.getWarehouse().getId()}</li>
</ul>
<#else>
    <p>Sale not found</p>
</#if>
</body>
</html>