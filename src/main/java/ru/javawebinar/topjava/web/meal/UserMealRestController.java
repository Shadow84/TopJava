package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.service.UserMealService;
import ru.javawebinar.topjava.to.UserMealWithExceed;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
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
        List<UserMealWithExceed> all = null;
        try {
            all = service.getAll(LoggedUser.id());
        } catch (NotFoundException e) {
            LOG.error(e.getMessage());
            return Collections.emptyList();
        }
        return all;
    }

    public UserMeal get(int id) {
        LOG.info("get usermeal " + id + " userId" + LoggedUser.id());
        UserMeal userMeal = null;
        try {
            userMeal = service.get(id, LoggedUser.id());
        } catch (NotFoundException e) {
            LOG.error(e.getMessage());
        }
        return userMeal;
    }

    public boolean delete(int id) {
        LOG.info("delete usermeal " + id + " userId" + LoggedUser.id());
        boolean delete = false;
        try {
            delete = service.delete(id, LoggedUser.id());
        } catch (NotFoundException e) {
            LOG.error(e.getMessage());
        }
        return delete;
    }

    public boolean update(UserMeal userMeal) {
        LOG.info("update usermeal " + userMeal + " userId" + LoggedUser.id());
        boolean update = false;
        try {
            update = service.update(userMeal, LoggedUser.id());
        } catch (NotFoundException e) {
            LOG.error(e.getMessage());
        }
        return update;
    }

    public boolean create(UserMeal userMeal) {
        LOG.info("create usermeal " + userMeal + " userId" + LoggedUser.id());
        return service.create(userMeal, LoggedUser.id());
    }

    public List<UserMealWithExceed> getByDescription(String description) {
        LOG.info("getBydescription usermeal " + description + " userId" + LoggedUser.id());
        List<UserMealWithExceed> byDescription = null;
        try {
            byDescription = service.getByDescription(description, LoggedUser.id());
        } catch (NotFoundException e) {
            LOG.error(e.getMessage());
            return Collections.emptyList();
        }
        return byDescription;
    }

    public List<UserMealWithExceed> getFilteredbyDateTime(LocalDate fromLocalDate, LocalTime fromLocalTime, LocalDate toLocalDate, LocalTime toLocalTime) {
        LOG.info("getFilteredByDateTime usermeal from LocalDate " + fromLocalDate + " LocalTime" + fromLocalTime + " to LocalDate " + toLocalDate + " toLocalTime " + toLocalTime + " userId " + LoggedUser.id());
        List<UserMealWithExceed> filteredByDateTime = null;
        if (fromLocalDate == null || toLocalDate == null || fromLocalTime == null || toLocalTime == null){
            Collections.emptyList();
        }
        try {
            filteredByDateTime = service.getFilteredByDateTime(fromLocalDate, fromLocalTime, toLocalDate, toLocalTime, LoggedUser.id());
        } catch (NotFoundException e) {
            LOG.error(e.getMessage());
            return Collections.emptyList();
        }
        return filteredByDateTime;
    }
}

