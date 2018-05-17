<!DOCTYPE HTML>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Подбери подарок другу</title>
    </head>
    <body>
        <form method="GET" action="/gifts-search?gender="$gender"&age="$age">
        <p><label>Пол:</label></p>
            <select name=gender>
                <option value="Женский">Женский</option>
                <option value="Мужской">Мужской</option>
            </select>
        <p><label>Возраст:</label></p>
            <select name=age>
                 <option value="Взрослые">Взрослые</option>
                 <option value="Дети">Дети</option>
            </select>
        <p><input type="submit" value="Найти"/></p>
        </form>
    </body>
</html>