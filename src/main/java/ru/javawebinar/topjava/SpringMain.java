package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 automatic resource management
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(new User(null, "userName", "email@mail.ru", "password", Role.ADMIN));
            adminUserController.getAll().forEach(user -> System.out.println(user.toString()));

            MealRestController controller = appCtx.getBean(MealRestController.class);
            controller.create(new Meal(LocalDateTime.of(2020, Month.JUNE, 17, 13, 0), "Pizza Time!", 10000));
            controller.getAll().forEach(mealTo -> System.out.println(mealTo.toString()));


            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));

        }
    }
}
