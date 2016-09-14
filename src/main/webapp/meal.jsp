<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Add or edit meal</title>
</head>
<body>

<form method="POST" action='meals' name="meal">
    Meal ID : <input type="text" readonly="readonly" name="Id"
                     value="<c:out value="${meal.id}" />" /> <br />
    Meal Description : <input
        type="text" name="Description"
        value="<c:out value="${meal.description}" />" /> <br />
    Meal Calories : <input
        type="text" name="Calories"
        value="<c:out value="${meal.calories}" />" /> <br />
    Meal Exceed : <input
        type="checkbox" name="Exceed"
        value="<c:out value="${meal.exceed}" />" /> <br /> <input
        type="submit" value="Submit" />
</form>
</body>
</html>