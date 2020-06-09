package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MockMealDAO;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.sevice.MealService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@WebServlet("/MealServlet")
public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    MealService dao = new MockMealDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // read the "command" parameter
            String theCommand = request.getParameter("command");

            // if the command is missing, then default to listing students
            if (theCommand == null) {
                theCommand = "LIST";
            }

            // route to the appropriate method
            switch (theCommand) {

                case "LIST":
                    listMeals(request, response);
                    break;

                case "ADD":
                    addMeal(request, response);
                    break;

                case "LOAD":
                    loadMeal(request, response);
                    break;

                case "UPDATE":
                    updateMeal(request, response);
                    break;

                case "DELETE":
                    deleteMeal(request, response);
                    break;

                default:
                    listMeals(request, response);
            }

        }
        catch (Exception exc) {
            throw new ServletException(exc);
        }


        log.debug("redirect to meals");
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    private void deleteMeal(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        // read meal id from form dao
        int theMealId = Integer.parseInt(request.getParameter("mealId"));

        // delete meal from database
        dao.deleteMeal(theMealId);

        // send them back to "list meals" page
        listMeals(request, response);
    }

    private void updateMeal(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        // read meal info from form data
        int id = Integer.parseInt(request.getParameter("mealId"));
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));

        // perform update on database
        dao.updateMeal(id, new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), description, calories));

        // send them back to the "list meals" page
        listMeals(request, response);

    }

    private void listMeals(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        // get meals from dao
        List<MealTo> mealsTo = dao.getMeals();

        // add meals to the request
        request.setAttribute("meals_list", mealsTo);

        // send to JSP page
        RequestDispatcher dispatcher = request.getRequestDispatcher("/meals.jsp");
        dispatcher.forward(request, response);
    }

    private void addMeal(HttpServletRequest request, HttpServletResponse response) throws Exception {

        // read meal info from form data
        Long dateTime = request.getDateHeader("dateTime");
        LocalDateTime date =
                LocalDateTime.ofInstant(Instant.ofEpochMilli(dateTime), ZoneId.systemDefault());
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));

        // create a new meal object
        Meal meal = new Meal(date, description, calories);

        // add the meal to the database
        dao.createMeal(meal);

        // send back to main page (the meals list)
        listMeals(request, response);
    }

    private void loadMeal(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        // read meal id from form data
        int theMealId = Integer.parseInt(request.getParameter("mealId"));

        // get meal from dao
        Meal theMeal = dao.getMeal(theMealId);

        // place meal in the request attribute
        request.setAttribute("the_meal", theMeal);

        // send to jsp page: update-student-form.jsp
        RequestDispatcher dispatcher =
                request.getRequestDispatcher("/update-meal-form.jsp");
        dispatcher.forward(request, response);
    }
}
