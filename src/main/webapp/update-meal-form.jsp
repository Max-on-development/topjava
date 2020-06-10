<!DOCTYPE html>
<html>

<head>
    <title>Update Meal</title>

    <link type="text/css" rel="stylesheet" href="css/style.css">
</head>

<body>
<div id="wrapper">
    <div id="header">
        <h2>Meals Table</h2>
    </div>
</div>

<div id="container">
    <h3>Update Meal</h3>

    <form action="MealServlet" method="POST">

        <input type="hidden" name="command" value="UPDATE" />

        <input type="hidden" name="mealId" value="${the_meal.getId()}" />

        <table>
            <tbody>
            <tr>
                <td><label>Date & Time:</label></td>
                <td><input type="datetime-local" name="dateTime"
                            value="${the_meal.getDateTime()}"/></td>
            </tr>

            <tr>
                <td><label>Description:</label></td>
                <td><input type="text" name="description"
                            value="${the_meal.getDescription()}"/></td>
            </tr>

            <tr>
                <td><label>Calories:</label></td>
                <td><input type="number" name="calories"
                            value="${the_meal.getCalories()}"/></td>
            </tr>

            <tr>
                <td><label></label></td>
                <td><input type="submit" value="Save" class="save" /></td>
            </tr>

            </tbody>
        </table>
    </form>

    <div style="clear: both;"></div>

    <p>
        <a href="MealServlet">Back to List</a>
    </p>
</div>
</body>

</html>