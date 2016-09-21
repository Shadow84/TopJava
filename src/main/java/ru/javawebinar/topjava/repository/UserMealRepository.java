package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.UserMeal;

import java.time.LocalDate;
import java.util.Collection;

/**
 * GKislin
 * 06.03.2015.
 */
public interface UserMealRepository {
    UserMeal save(UserMeal userMeal);

    boolean delete(int id);

    UserMeal get(int id);

    // null if not found
    Collection<UserMeal> getAll();

    Collection<UserMeal> getFilteredByDateTime(LocalDate fromLocalDate, LocalDate toLocalDate);
}
