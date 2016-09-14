package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.InMemoryMealDAOImpl;
import ru.javawebinar.topjava.dao.MealDAO;
import ru.javawebinar.topjava.dao.MealIdGenerator;
import ru.javawebinar.topjava.model.UserMealWithExceed;
import ru.javawebinar.topjava.util.UserMealsUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;


public class MealServlet extends HttpServlet {
    private static final Logger LOG = getLogger(MealServlet.class);
    private static final String LIST_MEALS = "/mealList.jsp";
    private static final String INSERT_OR_EDIT = "/meal.jsp";
    private MealDAO mealDAO = new InMemoryMealDAOImpl();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug("GET: redirect to mealList");

        String forward;
        String action = request.getParameter("action");
        request.setCharacterEncoding("UTF-8");
        if (action.equalsIgnoreCase("delete")) {
            int Id = Integer.parseInt(request.getParameter("Id"));
            mealDAO.deleteMeal(Id);
            forward = LIST_MEALS;
            request.setAttribute("mealList", mealDAO.getAll());
        } else if (action.equalsIgnoreCase("edit")) {
            forward = INSERT_OR_EDIT;
            int Id = Integer.parseInt(request.getParameter("Id"));
            UserMealWithExceed meal = mealDAO.getMeal(Id);
            request.setAttribute("meal", meal);
        } else if (action.equalsIgnoreCase("mealList")) {
            forward = LIST_MEALS;
            request.setAttribute("mealList", mealDAO.getAll());
        } else {
            forward = INSERT_OR_EDIT;
        }

        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug("POST: redirect to mealList");
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("Id");
        String calories = request.getParameter("Calories");
        String description = request.getParameter("Description");
        String exceed = request.getParameter("Exceed");

        if (id == null || id.isEmpty()) {
            UserMealWithExceed userMealWithExceed = new UserMealWithExceed(MealIdGenerator.generate(), LocalDateTime.now(), description, Integer.parseInt(calories), Boolean.parseBoolean(exceed));
            mealDAO.addMeal(userMealWithExceed);
        } else {
            UserMealWithExceed meal = mealDAO.getMeal(Integer.parseInt(id));
            meal.setCalories(Integer.parseInt(calories));
            meal.setDescription(description);
            meal.setExceed(Boolean.parseBoolean(exceed));
            mealDAO.updateMeal(meal);
        }

        List<UserMealWithExceed> filteredMealsWithExceeded = mealDAO.getAll();
        request.setAttribute("mealList", filteredMealsWithExceeded);
        request.getRequestDispatcher(LIST_MEALS).forward(request, response);
    }
}
