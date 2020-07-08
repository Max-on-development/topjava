package ru.javawebinar.topjava;

import org.springframework.context.support.GenericXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;

import java.util.Arrays;

public class SpringMain {
    public static void main(String[] args) {
        GenericXmlApplicationContext springContext = new GenericXmlApplicationContext();
        springContext.getEnvironment().setActiveProfiles(Profiles.getActiveDbProfile(), Profiles.REPOSITORY_IMPLEMENTATION);
        springContext.load("spring/spring-app.xml", "spring/spring-db.xml");
        springContext.refresh();
        System.out.println("Bean definition names: " + Arrays.toString(springContext.getBeanDefinitionNames()));

        MealService service = springContext.getBean(MealService.class);
        Meal meal = service.getWithUser(100003, 100000);
        System.out.println(meal.toString());

/*        Meal meal2 = service.get(100004, 100000);
        System.out.println(meal2.toString());

        List<Meal> list = service.getAll(100000);
        list.forEach(meal1 -> System.out.println(meal1));*/

/*        UserService userService = springContext.getBean(UserService.class);
        User u = userService.getWithMeals(100000);
        System.out.println(u.toString());*/
    }
}
