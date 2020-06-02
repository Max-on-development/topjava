package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(9, 0), LocalTime.of(14, 0), 2000);
        mealsTo.forEach(System.out::println);

        filteredByStreams(meals, LocalTime.of(0, 0), LocalTime.of(23, 59), 2000).forEach(System.out::println);



    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate, Integer> dailyTotalCallories = new HashMap<>();
        List<UserMeal> mealsBetweenTimeFrames = new ArrayList<>();
        for (UserMeal userMeal : meals) {
            dailyTotalCallories.merge(userMeal.getDateTime().toLocalDate(), userMeal.getCalories(), (x,y) -> x+y);
            if (TimeUtil.isBetweenHalfOpen(userMeal.getDateTime().toLocalTime(), startTime, endTime)) {
                mealsBetweenTimeFrames.add(userMeal);
            }
        }

        List<UserMealWithExcess> result = new ArrayList<>();
        for (UserMeal userMeal : mealsBetweenTimeFrames) {
            result.add(new UserMealWithExcess(
                    userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(),
                    dailyTotalCallories.get(userMeal.getDateTime().toLocalDate())>caloriesPerDay));
        }
        return result;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate, Integer> dailyTotalCallories = meals.stream()
                .collect(Collectors.groupingBy(x -> x.getDateTime().toLocalDate(),
                        Collectors.summingInt(UserMeal::getCalories)));

        List<UserMeal> mealsBetweenTimeFrames =
                meals.stream()
                        .filter(x -> TimeUtil.isBetweenHalfOpen(x.getDateTime().toLocalTime(), startTime, endTime))
                .collect(Collectors.toList());

        List<UserMealWithExcess> result = new ArrayList<>();
        mealsBetweenTimeFrames.forEach(x -> result.add(new UserMealWithExcess(x.getDateTime(), x.getDescription(), x.getCalories(),
                dailyTotalCallories.get(x.getDateTime().toLocalDate())>caloriesPerDay)));

        return result;
    }
}
