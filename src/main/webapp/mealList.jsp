<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Meal list</title>
    <style>
        .normal {
            color: green;
        }

        .exeded {
            color: red;
        }
    </style>
</head>
<body>
<h2><a href="index.html">Home</a></h2>
<h2>Meal list</h2>
<table border="1px" cellpadding="0" cellspacing="0">
    <thead>
    <tr>
        <th width="10%">ID</th>
        <th width="10%">Description</th>
        <th width="15%">Date Time</th>
        <th width="10%">Calories</th>
        <th colspan=2 width="10%">Action</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${mealList}" var="meal">
        <tr class="${meal.exceed ? 'exeded' : 'normal'}">
            <td align="center">${meal.id}</td>
            <td align="center">${meal.description}</td>
            <td align="center"><c:set var="cleanedDateTime" value="${fn:replace(meal.dateTime, 'T', ' ')}"/>
                <fmt:parseDate value="${ cleanedDateTime }" pattern="yyyy-MM-dd HH:mm" var="parsedDateTime"
                               type="both"/>
                <fmt:formatDate pattern="dd.MM.yyyy HH:mm" value="${ parsedDateTime }"/></td>
            <td align="center">${meal.calories}</td>
            <td><a href="meals?action=edit&Id=<c:out value="${meal.id}"/>">Update</a></td>
            <td><a href="meals?action=delete&Id=<c:out value="${meal.id}"/>">Delete</a></td>
        </tr>
    </c:forEach>
    <p><a href="meals?action=insert">Add Meal</a></p>
    </tbody>
</table>
</body>
</html>
