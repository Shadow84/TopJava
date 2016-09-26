package ru.javawebinar.topjava;

import ru.javawebinar.topjava.matcher.ModelMatcher;
import ru.javawebinar.topjava.model.UserMeal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static ru.javawebinar.topjava.model.BaseEntity.START_SEQ;

/**
 * GKislin
 * 13.03.2015.
 */
public class MealTestData {

    public static final ModelMatcher<UserMeal, String> MATCHER = new ModelMatcher<>(UserMeal::toString);

    public static final int MEAL_ID = START_SEQ + 2;

    public static final UserMeal MEAL1 = new UserMeal(MEAL_ID, LocalDateTime.of(2015, Month.MAY, 30, 10, 00), "Завтрак", 500);
    public static final UserMeal MEAL2 = new UserMeal(MEAL_ID + 1, LocalDateTime.of(2015, Month.MAY, 30, 13, 00), "Обед", 1000);
    public static final UserMeal MEAL3 = new UserMeal(MEAL_ID + 2, LocalDateTime.of(2015, Month.MAY, 30, 20, 00), "Ужин", 500);

    public static final UserMeal MEAL4 = new UserMeal(MEAL_ID + 3, LocalDateTime.of(2015, Month.MAY, 31, 10, 00), "Завтрак", 1000);
    public static final UserMeal MEAL5 = new UserMeal(MEAL_ID + 4, LocalDateTime.of(2015, Month.MAY, 31, 13, 00), "Обед", 500);
    public static final UserMeal MEAL6 = new UserMeal(MEAL_ID + 5, LocalDateTime.of(2015, Month.MAY, 31, 20, 00), "Ужин", 510);

    public static List<UserMeal> meals = Arrays.asList(MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1);


    public static final UserMeal UPDATEDMEAL4 = new UserMeal(MEAL_ID + 3, LocalDateTime.of(2015, Month.MAY, 31, 10, 00), "Обновленный Завтрак", 1500);

    public static final UserMeal NEWMEAL6 = new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 30), "Новый Завтрак", 1500);
}
