package ru.javawebinar.topjava.sevice;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.util.List;

public interface MealService {
    List<MealTo> getMeals();

    void createMeal(Meal meal);

    Meal getMeal(int id);

    void deleteMeal(int id);

    void updateMeal(int id, Meal newMeal);
}
