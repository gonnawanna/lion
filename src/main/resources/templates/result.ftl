<html>
<head>
    <meta charset="UTF-8">
    <title>Подарочные наборы</title>
</head>
    <body>
    <#if giftSets?has_content>
        <ul>
            <#list giftSets as giftSet>
                <li>Подарочный набор: ${giftSet.firstProduct} + ${giftSet.secondProduct} + ${giftSet.thirdProduct}
                <h3>Цена: ${giftSet.price} руб.</h3></li>
            </#list>
        </ul>
    <#else>
        <p>No gifts yet</p>
    </#if>
    </body>
</html>