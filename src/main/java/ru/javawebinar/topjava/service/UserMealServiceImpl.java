package ru.javawebinar.topjava.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;
import ru.javawebinar.topjava.to.UserMealWithExceed;
import ru.javawebinar.topjava.util.UserMealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * GKislin
 * 06.03.2015.
 */
@Service
public class UserMealServiceImpl implements UserMealService {
    private static final Logger LOG = LoggerFactory.getLogger(UserMealServiceImpl.class);

    @Autowired
    private UserMealRepository repository;

    @Override
    public List<UserMealWithExceed> getAll(int userId) throws NotFoundException {
        LOG.info("getAll usermeals userId " + userId);
        Collection<UserMeal> result = repository.getAll()
                .stream()
                .filter(um -> um.getUserId() == userId)
                .collect(Collectors.toList());

        if (result == null && result.isEmpty()) {
            throw new NotFoundException("not have usermeal to user" + userId);
        }
        return UserMealsUtil.getWithExceeded(result, UserMealsUtil.DEFAULT_CALORIES_PER_DAY);
    }

    @Override
    public UserMeal get(int id, int userId) throws NotFoundException {
        LOG.info("get usermeal " + id + " userId" + userId);
        UserMeal result = repository.get(id);
        if (result == null && result.getUserId() != userId) {
            throw new NotFoundException("not have usermeal to user" + userId);
        }
        return result;
    }

    @Override
    public boolean delete(int id, int userId) throws NotFoundException {
        LOG.info("delete usermeal " + id + " userId" + userId);

        UserMeal userMeal = repository.get(id);
        if (userMeal.getUserId() != userId) {
            throw new NotFoundException("not have usermeal to user" + userId);
        } else if (!repository.delete(id)) {
            throw new NotFoundException("not have usermeal to user" + userId);
        }
        return true;
    }

    @Override
    public List<UserMealWithExceed> getByDescription(String description, int userId) throws NotFoundException {
        LOG.info("getByDescription  " + description + " userId" + userId);
        Collection<UserMeal> result = repository.getByDescription(description)
                .stream()
                .filter(um -> um.getUserId() == userId)
                .collect(Collectors.toList());

        if (result == null && result.isEmpty()) {
            throw new NotFoundException("not have usermeal to user" + userId);
        }
        return UserMealsUtil.getWithExceeded(result, UserMealsUtil.DEFAULT_CALORIES_PER_DAY);
    }

    @Override
    public List<UserMealWithExceed> getFilteredbyDateTime(LocalDate fromLocalDate, LocalTime fromLocalTime, LocalDate toLocalDate, LocalTime toLocalTime, int userId) throws NotFoundException {
        LOG.info("getFilteredByDateTime usermeal from LocalDate " + fromLocalDate + " LocalTime" + fromLocalTime + " to LocalDate " + toLocalDate + " toLocalTime " + toLocalTime + " userId " + userId);
        Collection<UserMeal> result = repository.getFilteredByDateTime(fromLocalDate, fromLocalTime, toLocalDate, toLocalTime)
                .stream()
                .filter(um -> um.getUserId() == userId)
                .collect(Collectors.toList());

        if (result == null && result.isEmpty()) {
            throw new NotFoundException("not have usermeal to user" + userId);
        }
        return UserMealsUtil.getWithExceeded(result, UserMealsUtil.DEFAULT_CALORIES_PER_DAY);
    }


    @Override
    public boolean update(UserMeal userMeal, int userId) throws NotFoundException {
        LOG.info("update usermeal " + userMeal + " userId" + userId);
        if (userMeal.getUserId() == userId) {
            return repository.save(userMeal) != null;
        }
        return false;
    }

    @Override
    public boolean create(UserMeal userMeal, int userId) throws NotFoundException {
        LOG.info("update usermeal " + userMeal + " userId" + userId);
        userMeal.setUserId(userId);
        return repository.save(userMeal) != null;
    }
}
