package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.to.UserMealWithExceed;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * GKislin
 * 15.06.2015.
 */
public interface UserMealService {

    List<UserMealWithExceed> getAll(int userId) throws NotFoundException;

    UserMeal get(int id, int userId) throws NotFoundException;

    boolean delete(int id, int userId) throws NotFoundException;

    List<UserMealWithExceed> getByDescription(String description, int userId) throws NotFoundException;

    List<UserMealWithExceed> getFilteredByDateTime(LocalDate fromLocalDate, LocalTime fromLocalTime, LocalDate toLocalDate, LocalTime toLocalTime, int userId) throws NotFoundException;

    boolean update(UserMeal userMeal, int userId) throws NotFoundException;

    boolean create(UserMeal userMeal, int userId) throws NotFoundException;
}
