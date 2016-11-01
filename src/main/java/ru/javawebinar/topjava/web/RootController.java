package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.service.UserMealService;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.util.TimeUtil;
import ru.javawebinar.topjava.util.UserMealsUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * User: gkislin
 * Date: 22.08.2014
 */
@Controller
public class RootController {

    private static final Logger LOG = getLogger(RootController.class);


    @Autowired
    private UserService userService;

    @Autowired
    private UserMealService userMealService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String root() {
        return "index";
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String userList(Model model) {
        model.addAttribute("userList", userService.getAll());
        return "userList";
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public String setUser(HttpServletRequest request) {
        int userId = Integer.valueOf(request.getParameter("userId"));
        AuthorizedUser.setId(userId);
        return "redirect:meals";
    }

    @RequestMapping(value = "/meals", method = RequestMethod.GET)
    public String mealList(HttpServletRequest request) {
        String action = request.getParameter("action");

        if (action == null) {
            LOG.info("getAll");
            request.setAttribute("mealList", UserMealsUtil.getWithExceeded(userMealService.getAll(AuthorizedUser.id()), AuthorizedUser.getCaloriesPerDay()));
            return"mealList";
        } else if (action.equals("delete")) {
            int id = getId(request);
            LOG.info("Delete {}", id);
            userMealService.delete(id, AuthorizedUser.id());
            return "redirect:meals";
        } else if (action.equals("create") || action.equals("update")) {
            final UserMeal meal = action.equals("create") ?
                    new UserMeal(LocalDateTime.now().withNano(0).withSecond(0), "", 1000) :
                    userMealService.get(getId(request), AuthorizedUser.id());             // update
            request.setAttribute("meal", meal);
            return"mealEdit";
        }
        return"mealList";
    }

    @RequestMapping(value = "/meals", method = RequestMethod.POST)
    public String setMeal(HttpServletRequest request) {
        String action = request.getParameter("action");
        if (action == null) {
            final UserMeal userMeal = new UserMeal(
                    LocalDateTime.parse(request.getParameter("dateTime")),
                    request.getParameter("description"),
                    Integer.valueOf(request.getParameter("calories")));

            if (request.getParameter("id").isEmpty()) {
                LOG.info("Create {}", userMeal);
                userMeal.setId(null);
                int userId = AuthorizedUser.id();
                userMealService.save(userMeal,userId);
            } else {
                LOG.info("Update {}", userMeal);
                userMeal.setId(getId(request));
                userMealService.update(userMeal, AuthorizedUser.id());
            }
            return "redirect:meals";

        } else if (action.equals("filter")) {
            LocalDate startDate = TimeUtil.parseLocalDate(resetParam("startDate", request));
            LocalDate endDate = TimeUtil.parseLocalDate(resetParam("endDate", request));
            LocalTime startTime = TimeUtil.parseLocalTime(resetParam("startTime", request));
            LocalTime endTime = TimeUtil.parseLocalTime(resetParam("endTime", request));
            request.setAttribute("mealList", UserMealsUtil.getFilteredWithExceeded(
                    userMealService.getBetweenDates(
                            startDate != null ? startDate : TimeUtil.MIN_DATE, endDate != null ? endDate : TimeUtil.MAX_DATE, AuthorizedUser.id()),
                    startTime != null ? startTime : LocalTime.MIN, endTime != null ? endTime : LocalTime.MAX, AuthorizedUser.getCaloriesPerDay()));
            return"mealList";
        }
        return "redirect:meals";
    }

    private String resetParam(String param, HttpServletRequest request) {
        String value = request.getParameter(param);
        request.setAttribute(param, value);
        return value;
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }
}
