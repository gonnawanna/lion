<html>
<head>
    <meta charset="UTF-8">
    <title>Подарочные наборы</title>
</head>
    <body>
    <#if giftSets?has_content>
        <ul>
            <#list giftSets as giftSet>
                <li>Подарочный набор: ${giftSet.firstProductName} + ${giftSet.secondProductName} + ${giftSet.thirdProductName}.
                Цена: ${giftSet.price} руб.</li>
            </#list>
        </ul>
    <#else>
        <p>No gifts yet</p>
    </#if>
    </body>
</html>