package ru.javawebinar.topjava.dao;


import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.sevice.MealService;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;


public class MockMealDAO implements MealService {

    private static final AtomicInteger AUTO_ID = new AtomicInteger(0);
    private static Map<Integer, Meal> allMeals = new HashMap<>();


    static  {
          List<Meal> meals = Arrays.asList(
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        meals.forEach(meal -> allMeals.put(AUTO_ID.getAndDecrement(), meal));
    }

    @Override
    public List<MealTo> getMeals() {
        List<Meal> meals = new ArrayList<>(allMeals.values());
        List<MealTo> mealTo = MealsUtil.filteredByStreams(meals, LocalTime.of(0, 0), LocalTime.of(23, 59), 2000);
        return mealTo;
    }

    @Override
    public void createMeal(Meal meal) {
        meal.setId(AUTO_ID.getAndIncrement());
        allMeals.put(meal.getId(), meal);
    }

    @Override
    public Meal getMeal(int id) {
        return allMeals.get(id);
    }

    @Override
    public void deleteMeal(int id) {
        allMeals.remove(id);
    }

    @Override
    public void updateMeal(int id, Meal newMeal) {
        allMeals.put(id, newMeal);
    }
}
