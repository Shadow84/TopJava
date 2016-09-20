package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.service.UserMealService;
import ru.javawebinar.topjava.to.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */
@Controller
public class UserMealRestController {
    protected final Logger LOG = LoggerFactory.getLogger(getClass());
    @Autowired
    private UserMealService service;

    public List<UserMealWithExceed> getAll() {
        LOG.info("getAll usermeals userId " + LoggedUser.id());
        return service.getAll(LoggedUser.id());
    }

    public UserMeal get(int id) {
        LOG.info("get usermeal " + id + " userId" + LoggedUser.id());
        return service.get(id, LoggedUser.id());
    }

    public boolean delete(int id) {
        LOG.info("delete usermeal " + id + " userId" + LoggedUser.id());
        return service.delete(id, LoggedUser.id());
    }

    public boolean update(UserMeal userMeal) {
        LOG.info("update usermeal " + userMeal + " userId" + LoggedUser.id());
        return service.update(userMeal, LoggedUser.id());
    }

    public boolean create(UserMeal userMeal) {
        LOG.info("create usermeal " + userMeal + " userId" + LoggedUser.id());
        return service.create(userMeal, LoggedUser.id());
    }

    public List<UserMealWithExceed> getByDescription(String description) {
        LOG.info("getBydescription usermeal " + description + " userId" + LoggedUser.id());
        return service.getByDescription(description, LoggedUser.id());
    }

    public List<UserMealWithExceed> getFilteredbyDateTime(LocalDate fromLocalDate, LocalTime fromLocalTime, LocalDate toLocalDate, LocalTime toLocalTime, int userId) {
        LOG.info("getFilteredByDateTime usermeal from LocalDate " + fromLocalDate + " LocalTime" + fromLocalTime + " to LocalDate " + toLocalDate + " toLocalTime " + toLocalTime + " userId " + LoggedUser.id());
        return service.getFilteredbyDateTime(fromLocalDate, fromLocalTime, toLocalDate, toLocalTime, LoggedUser.id());
    }
}

