<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<html>
<head>
    <title>Meal list</title>
    <style>
        .normal {
            color: green;
        }

        .exceeded {
            color: red;
        }
    </style>
</head>
<body>
<section>
    <h2><a href="index.html">Home</a></h2>
    <h3>Meal list</h3>
    <a href="meals?action=create">Add Meal</a>
    <hr>

    <jsp:useBean id="DateTimeFilterTO" type="ru.javawebinar.topjava.to.DateTimeFilterTO" scope="request"/>
    <form method="get" action="meals">
        <table>
            <tr>
                <td>
                    <dl>
                        <dt>FromLocalDate:</dt>
                        <dd><input type="datetime-local" value="${DateTimeFilterTO.fromLocalDate}" name="fromLocalDate">
                        </dd>
                    </dl>
                    <dl>
                        <dt>FromLocalTime:</dt>
                        <dd><input type="datetime-local" value="${DateTimeFilterTO.fromLocalTime}" name="fromLocalTime">
                        </dd>
                    </dl>
                </td>
                <td>
                    <dl>
                        <dt>ToLocalDate:</dt>
                        <dd><input type="datetime-local" value="${DateTimeFilterTO.toLocalDate}" name="toLocalDate">
                        </dd>
                    </dl>
                    <dl>
                        <dt>ToLocalTime:</dt>
                        <dd><input type="datetime-local" value="${DateTimeFilterTO.toLocalTime}" name="toLocalTime">
                        </dd>
                    </dl>
                </td>
            </tr>
        </table>
        <button type="submit"style="margin-left:30%;display:block;margin-bottom:0%">Find by date</button>
    </form>
    <dl>
        <form action="meals" method="get">
        <dt>Search by Description:</dt>
        <input type="submit" value="Search" />
        <input type="text" value="" size=20 name="SearchString">
        </form>
    </dl>
    Meal List
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${mealList}" var="meal">
            <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.to.UserMealWithExceed"/>
            <tr class="${meal.exceed ? 'exceeded' : 'normal'}">
                <td>
                        <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                        <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                        ${fn:formatDateTime(meal.dateTime)}
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
                <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>