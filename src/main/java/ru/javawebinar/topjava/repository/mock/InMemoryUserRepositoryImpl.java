package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * GKislin
 * 15.06.2015.
 */
@Repository
public class InMemoryUserRepositoryImpl implements UserRepository {
    private Map<Integer, User> repository = new ConcurrentHashMap<>();
    private static final Logger LOG = LoggerFactory.getLogger(InMemoryUserRepositoryImpl.class);

    @Override
    public boolean delete(int id) {
        LOG.info("delete user " + id);
        return repository.remove(id) != null;
    }

    @Override
    public User save(User user) {
        LOG.info("save user " + user);
        repository.put(user.getId(), user);
        return user;
    }

    @Override
    public User get(int id) {
        LOG.info("get user " + id);
        return repository.get(id);
    }

    @Override
    public List<User> getAll() {
        LOG.info("getAll users");
        List<User> values = new ArrayList<>(repository.values());
        values.sort((u1, u2) -> u1.getName().compareTo(u2.getName()));
        return Collections.emptyList();
    }

    @Override
    public User getByEmail(String email) {
        LOG.info("user getByEmail " + email);
        User findedUser = repository
                .values()
                .stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst().get();
        return findedUser;
    }
}
