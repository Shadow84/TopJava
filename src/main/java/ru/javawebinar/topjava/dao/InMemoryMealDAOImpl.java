package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.UserMealWithExceed;
import ru.javawebinar.topjava.util.UserMealsUtil;

import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

/**
 * Created by Privat on 14.09.2016.
 */
public class InMemoryMealDAOImpl implements MealDAO {
    private static final List<UserMealWithExceed> filteredMealsWithExceeded = Collections.synchronizedList(UserMealsUtil.getFilteredWithExceeded(UserMealsUtil.mealList, LocalTime.MIN, LocalTime.MAX, UserMealsUtil.CALORIESPERDAY));

    @Override
    public void addMeal(UserMealWithExceed mealWithExceed) {
        mealWithExceed.setId(MealIdGenerator.generate());
        filteredMealsWithExceeded.add(mealWithExceed);
    }

    @Override
    public void updateMeal(UserMealWithExceed mealWithExceed) {
        UserMealWithExceed userMealWithExceed = getMeal(mealWithExceed.getId());

        userMealWithExceed.setCalories(mealWithExceed.getCalories());
        userMealWithExceed.setDescription(mealWithExceed.getDescription());
        userMealWithExceed.setExceed(mealWithExceed.isExceed());

        filteredMealsWithExceeded.set(mealWithExceed.getId() - 1, userMealWithExceed);
    }

    @Override
    public UserMealWithExceed getMeal(int id) {
        UserMealWithExceed userMealWithExceed = filteredMealsWithExceeded
                .stream()
                .filter(um -> um.getId() == id)
                .findFirst().get();
        return userMealWithExceed;
    }

    @Override
    public void deleteMeal(int id) {
        UserMealWithExceed userMealWithExceed = filteredMealsWithExceeded
                .stream()
                .filter(um -> um.getId() == id)
                .findFirst().get();
        filteredMealsWithExceeded.remove(userMealWithExceed);
    }

    @Override
    public List<UserMealWithExceed> getAll() {
        return filteredMealsWithExceeded;
    }
}
