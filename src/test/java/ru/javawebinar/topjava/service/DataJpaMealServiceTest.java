package ru.javawebinar.topjava.service;

import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles(profiles = {"hsqldb", "datajpa"})
public class DataJpaMealServiceTest extends MealServiceTest {
}
