package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;
import ru.javawebinar.topjava.util.TimeUtil;
import ru.javawebinar.topjava.util.UserMealsUtil;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * GKislin
 * 15.09.2015.
 */
@Repository
public class InMemoryUserMealRepositoryImpl implements UserMealRepository {
    private static final Logger LOG = LoggerFactory.getLogger(InMemoryUserMealRepositoryImpl.class);
    private Map<Integer, UserMeal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        UserMealsUtil.MEAL_LIST.forEach((userMeal) -> {
            userMeal.setUserId(10);
            save(userMeal);
        });
    }

    @Override
    public UserMeal save(UserMeal userMeal) {
        LOG.info("save usermeal" + userMeal);
        if (userMeal.isNew()) {
            userMeal.setId(counter.incrementAndGet());
        }
        repository.put(userMeal.getId(), userMeal);
        return userMeal;
    }

    @Override
    public boolean delete(int id) {
        LOG.info("delete usermeal" + id);
        return repository.remove(id) != null;
    }

    @Override
    public UserMeal get(int id) {
        LOG.info("get usermeal" + id);
        return repository.get(id);
    }

    @Override
    public Collection<UserMeal> getAll() {
        LOG.info("getAll usermeals");
        List<UserMeal> result = repository.values()
                .stream()
                .collect(Collectors.toList());

        result.sort((um1, um2) -> um2.getDateTime().compareTo(um1.getDateTime()));
        return result;
    }

    @Override
    public Collection<UserMeal> getByDescription(String description) {
        LOG.info("usermeal getByDescription");
        List<UserMeal> result = repository.values()
                .stream()
                .filter(userMeal -> userMeal.getDescription().equals(description))
                .collect(Collectors.toList());

        result.sort((um1, um2) -> um2.getDateTime().compareTo(um1.getDateTime()));
        return result;
    }

    @Override
    public Collection<UserMeal> getFilteredByDateTime(LocalDate fromLocalDate, LocalDate toLocalDate) {
        LOG.info("getFilteredByDateTime usermeal from LocalDate " + fromLocalDate +  " to LocalDate " + toLocalDate);
        List<UserMeal> result = repository.values()
                .stream()
                .filter(userMeal -> TimeUtil.isBetweenDate(userMeal.getDateTime().toLocalDate(), fromLocalDate, toLocalDate))
                .collect(Collectors.toList());

        result.sort((um1, um2) -> um2.getDateTime().compareTo(um1.getDateTime()));
        return result;
    }
}

