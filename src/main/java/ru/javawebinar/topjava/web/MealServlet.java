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
import java.time.LocalDateTime;
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
        int theMealId = Integer.parseInt(request.getParameter("mealId"));
        dao.deleteMeal(theMealId);
        listMeals(request, response);
    }

    private void updateMeal(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        int id = Integer.parseInt(request.getParameter("mealId"));
        LocalDateTime date = LocalDateTime.parse(request.getParameter("dateTime"));
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        dao.updateMeal(id, new Meal(date, description, calories));
        listMeals(request, response);

    }

    private void listMeals(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        List<MealTo> mealsTo = dao.getMeals();
        request.setAttribute("meals_list", mealsTo);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/meals.jsp");
        dispatcher.forward(request, response);
    }

    private void addMeal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        LocalDateTime date = LocalDateTime.parse(request.getParameter("dateTime"));
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        Meal meal = new Meal(date, description, calories);
        dao.createMeal(meal);
        listMeals(request, response);
    }

    private void loadMeal(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        int theMealId = Integer.parseInt(request.getParameter("mealId"));
        Meal theMeal = dao.getMeal(theMealId);
        request.setAttribute("the_meal", theMeal);
        RequestDispatcher dispatcher =
                request.getRequestDispatcher("/update-meal-form.jsp");
        dispatcher.forward(request, response);
    }
}
