<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <style>
        #header {width: 100%; background: white; margin-top: 0px; padding:15px 0px 15px 0px; color: black; text-align: center}
        #meals {
            font-family: "Trebuchet MS", Arial, Helvetica, sans-serif;
            border-collapse: collapse;
            width: 100%;
        }

        #meals td, #customers th {
            border: 1px solid #ddd;
            padding: 8px;
        }

        #meals tr:nth-child(even){background-color: #f2f2f2;}

        #meals tr:hover {background-color: #ddd;}

        #meals th {
            padding-top: 12px;
            padding-bottom: 12px;
            text-align: left;
            background-color: rgba(201, 89, 89, 0.9);
            color: white;
        }
    </style>

    <title>Meals</title>
   <!--<link type="text/css" rel="stylesheet" href="css/style.css">-->
</head>
<body>
    <div id="wrapper">
        <div id="header">
            <h2>Meals Table</h2>
        </div>
    </div>

    <div id="container">
        <div id="content">

            <input type="button" value="Add Meal"
                   onclick="window.location.href='add-meal-form.jsp'; return false;"
                   class="add-meal-button"
            />

            <table id="meals">
                <tr>
                    <th>Date & Time</th>
                    <th>Description</th>
                    <th>Calories</th>
                    <th>Excess</th>
                    <th>Action</th>
                </tr>

                <c:forEach var="mealTo" items="${meals_list}">
                    <c:url var="tempLink" value="MealServlet">
                        <c:param name="command" value="LOAD" />
                        <c:param name="mealId" value="${mealTo.getId()}" />
                    </c:url>

                    <c:url var="deleteLink" value="MealServlet">
                        <c:param name="command" value="DELETE" />
                        <c:param name="mealId" value="${mealTo.getId()}" />
                    </c:url>
                    <tr style="color:${mealTo.isExcess() ? 'rgba(201, 89, 89, 0.9)' : null}">
                        <td>
                            <fmt:parseDate value="${mealTo.getDateTime()}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime"
                                           type="both"/>
                            <fmt:formatDate pattern="dd.MM.yyyy HH:mm" value="${parsedDateTime}"/>
                        </td>
                        <td>${mealTo.getDescription()}</td>
                        <td>${mealTo.getCalories()}</td>
                        <td>${mealTo.isExcess()}</td>
                        <td>
                            <a href="${tempLink}">Update</a>
                            |
                            <a href="${deleteLink}"
                               onclick="if (!(confirm('Are you sure you want to delete this meal?'))) return false">
                                Delete</a>
                        </td>
                    </tr>
                </c:forEach>

            </table>
        </div>
    </div>

    <p><a href="index.html">go back home</a></p>


</body>
</html>
