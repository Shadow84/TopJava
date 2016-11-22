package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;
import ru.javawebinar.topjava.to.UserMealTo;
import ru.javawebinar.topjava.util.UserMealsUtil;
import ru.javawebinar.topjava.util.exception.ExceptionUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * GKislin
 * 06.03.2015.
 */
@Service
public class UserMealServiceImpl implements UserMealService {

    @Autowired
    private UserMealRepository repository;

    @Override
    public UserMeal get(int id, int userId) {
        return ExceptionUtil.checkNotFoundWithId(repository.get(id, userId), id);
    }

    @Override
    public void delete(int id, int userId) {
        ExceptionUtil.checkNotFoundWithId(repository.delete(id, userId), id);
    }

    @Override
    public Collection<UserMeal> getBetweenDateTimes(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return repository.getBetween(startDateTime, endDateTime, userId);
    }

    @Override
    public Collection<UserMeal> getAll(int userId) {
        return repository.getAll(userId);
    }

    @Override
    public UserMeal update(UserMeal meal, int userId) {
        return ExceptionUtil.checkNotFoundWithId(repository.save(meal, userId), meal.getId());
    }

    @Transactional
    @Override
    public UserMeal update(UserMealTo userMealTo, int userId) throws NotFoundException {
        UserMeal userMeal = get(userMealTo.getId(), userId);
        return ExceptionUtil.checkNotFoundWithId(repository.save(UserMealsUtil.updateFromTo(userMeal, userMealTo), userId), userMeal.getId());
    }

    /* @CacheEvict(value = "users", allEntries = true)
    @Transactional
    @Override
    public void update(UserTo userTo) {
        User user = get(userTo.getId());
        repository.save(UserUtil.updateFromTo(user, userTo));
    }*/

    @Override
    public UserMeal save(UserMeal meal, int userId) {
        return repository.save(meal, userId);
    }

//    @Autowired
//    private UserRepository userRepository;

    //    @Transactional
    public UserMeal getWithUser(Integer id, Integer userId) {
//        UserMeal meal = get(id, userId);
//        meal.setUser(userRepository.get(userId));
//        return meal;
        return ExceptionUtil.checkNotFoundWithId(repository.getWithUser(id, userId), id);
    }
}
