package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.to.DateTimeFilterTO;
import ru.javawebinar.topjava.web.meal.UserMealRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * User: gkislin
 * Date: 19.08.2014
 */
public class MealServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(MealServlet.class);

    private UserMealRestController userMealRestController;
    ConfigurableApplicationContext appCtx;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        userMealRestController = appCtx.getBean(UserMealRestController.class);

    }

    @Override
    public void destroy() {
        super.destroy();
        appCtx.close();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        UserMeal userMeal = new UserMeal(id.isEmpty() ? null : Integer.valueOf(id),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.valueOf(request.getParameter("calories")),
                Integer.valueOf(request.getParameter("userId")));
        LOG.info(userMeal.isNew() ? "Create {}" : "Update {}", userMeal);
        userMealRestController.update(userMeal);
        response.sendRedirect("meals");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            LOG.info("getAll");

            String fromLocalDateParam = request.getParameter("fromLocalDate");
            String fromLocaltimeParam = request.getParameter("fromLocalTime");
            String toLocalDateParam = request.getParameter("toLocalDate");
            String toLocalTimeParam = request.getParameter("toLocalTime");

            if (checkDateParams(fromLocalDateParam, fromLocaltimeParam, toLocalDateParam, toLocalTimeParam)) {
                LocalDateTime fromLocalDate = LocalDateTime.parse(fromLocalDateParam);
                LocalDateTime fromLocalTime = LocalDateTime.parse(fromLocaltimeParam);
                LocalDateTime toLocalDate = LocalDateTime.parse(toLocalDateParam);
                LocalDateTime toLocalTime = LocalDateTime.parse(toLocalTimeParam);

                request.setAttribute("mealList", userMealRestController.getFilteredbyDateTime(fromLocalDate.toLocalDate(), fromLocalTime.toLocalTime(), toLocalDate.toLocalDate(), toLocalTime.toLocalTime()));
            } else {
                String searchString = request.getParameter("SearchString");
                if (searchString == null || searchString.isEmpty()) {
                    request.setAttribute("mealList", userMealRestController.getAll());
                } else {
                    request.setAttribute("mealList", userMealRestController.getByDescription(searchString));
                }
            }
            request.setAttribute("DateTimeFilterTO", new DateTimeFilterTO());
            request.getRequestDispatcher("/mealList.jsp").forward(request, response);
        } else if (action.equals("delete")) {
            int id = getId(request);
            LOG.info("Delete {}", id);
            userMealRestController.delete(id);
            response.sendRedirect("meals");
        } else if (action.equals("create") || action.equals("update")) {
            final UserMeal meal = action.equals("create") ?
                    new UserMeal(LocalDateTime.now().withNano(0).withSecond(0), "", 1000, 1) :
                    userMealRestController.get(getId(request));
            request.setAttribute("meal", meal);
            request.getRequestDispatcher("mealEdit.jsp").forward(request, response);
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }

    private int getUserId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("userId"));
        return Integer.valueOf(paramId);
    }

    private boolean checkDateParams(String fromLocalDateParam, String fromLocaltimeParam, String toLocalDateParam, String toLocalTimeParam) {
        return (fromLocalDateParam != null && !fromLocalDateParam.isEmpty()) &&
                (fromLocaltimeParam != null && !fromLocaltimeParam.isEmpty()) &&
                (toLocalDateParam != null && !toLocalDateParam.isEmpty()) &&
                (toLocalTimeParam != null && !toLocalTimeParam.isEmpty());
    }
}
