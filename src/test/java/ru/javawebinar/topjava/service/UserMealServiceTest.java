package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.util.DbPopulator;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;


@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class UserMealServiceTest {

    @Autowired
    protected UserMealService service;

    @Autowired
    private DbPopulator dbPopulator;

    @Before
    public void setUp() throws Exception {
        dbPopulator.execute();
    }


    @Test
    public void get() throws Exception {
        UserMeal userMeal = service.get(MEAL_ID, USER_ID);
        MATCHER.assertEquals(MEAL1, userMeal);
    }

    @Test(expected = NotFoundException.class)
    public void getSomeoneElseMeal() throws Exception {
        service.get(MEAL_ID, ADMIN_ID);
    }

    @Test
    public void delete() throws Exception {
        service.delete(MEAL_ID, USER_ID);
        List<UserMeal> userMeals = new ArrayList<>(meals);
        userMeals.remove(MEAL1);
        MATCHER.assertCollectionEquals(userMeals, service.getAll(USER_ID));
    }

    @Test(expected = NotFoundException.class)
    public void deleteSomeoneElseMeal() throws Exception {
        service.delete(MEAL_ID, ADMIN_ID);
    }

    @Test
    public void getBetweenDates() throws Exception {
        Collection<UserMeal> betweenDates = service.getBetweenDates(LocalDate.of(2015, Month.MAY, 30), LocalDate.of(2015, Month.MAY, 30), USER_ID);
        List<UserMeal> userMeals = Arrays.asList(MEAL1, MEAL2, MEAL3);
        MATCHER.assertCollectionEquals(userMeals, betweenDates);
    }

    @Test
    public void getBetweenDateTimes() throws Exception {
        Collection<UserMeal> betweenDates = service.getBetweenDateTimes(LocalDateTime.of(2015, Month.MAY, 30, 9, 00), LocalDateTime.of(2015, Month.MAY, 30, 11, 00), USER_ID);
        List<UserMeal> userMeals = Arrays.asList(MEAL1);
        MATCHER.assertCollectionEquals(userMeals, betweenDates);
    }

    @Test
    public void getAll() throws Exception {
        Collection<UserMeal> all = service.getAll(USER_ID);
        MATCHER.assertCollectionEquals(meals, all);
    }

    @Test
    public void update() throws Exception {
        UserMeal updatedMeal = service.update(MealTestData.UPDATEDMEAL4, USER_ID);
        MATCHER.assertEquals(updatedMeal, service.get(MEAL_ID + 3, USER_ID));
    }


    @Test(expected = NotFoundException.class)
    public void updateSomeoneElseMeal() throws Exception {
        service.update(MealTestData.UPDATEDMEAL4, ADMIN_ID);
    }

    @Test
    public void save() throws Exception {
        UserMeal savedMeal = service.save(NEWMEAL6, USER_ID);
        MATCHER.assertEquals(savedMeal, service.get(MEAL_ID + 6, USER_ID));
    }

}