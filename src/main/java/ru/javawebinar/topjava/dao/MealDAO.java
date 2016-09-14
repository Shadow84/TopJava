package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.util.List;

/**
 * Created by Privat on 14.09.2016.
 */
public interface MealDAO {
    void addMeal(UserMealWithExceed mealWithExceed);

    void updateMeal(UserMealWithExceed mealWithExceed);

    UserMealWithExceed getMeal(int id);

    void deleteMeal(int id);

    List<UserMealWithExceed> getAll();
}
