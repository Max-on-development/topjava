package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

@Controller
public class JspMealController {
    private static final Logger log = LoggerFactory.getLogger(JspMealController.class);

    @Autowired
    private MealService service;

    @GetMapping("/meals")
    public String getMeals(Model model) {
        log.info("getAll for user {}", getUserId());
        List<MealTo> list = MealsUtil.getTos(service.getAll(getUserId()), SecurityUtil.authUserCaloriesPerDay());
        model.addAttribute("meals", list);
        return "meals";
    }

    @PostMapping("/meals")
    public String delete(HttpServletRequest request) {
        log.info("delete for user {}", getUserId());
        String action = request.getParameter("action");
        int id = getId(request);
        if (action == "delete") service.delete(id, getUserId());
        return "redirect:meals";
    }

    private int getUserId(){
        return SecurityUtil.authUserId();
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }

    /*    @PostMapping("/meals")
    public String create(HttpServletRequest request) {

        int id = (int) request.getAttribute("id");
        LocalDateTime dateTime = (LocalDateTime) request.getAttribute("dateTime");
        String description = (String) request.getAttribute("description");
        int calories = (int) request.getAttribute("calories");
        Meal meal = new Meal(id, dateTime, description, calories);
        checkNew(meal);
        int userId = SecurityUtil.authUserId();
        log.info("create {} for user {}", meal, userId);
        request.setAttribute("meal", meal);

        //service.create(meal, getUserId());
        return "redirect:meals";
    }*/


/*    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }*/
}
