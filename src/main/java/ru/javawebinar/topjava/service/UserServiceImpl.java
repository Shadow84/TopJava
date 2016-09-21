package ru.javawebinar.topjava.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.exception.ExceptionUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */
@Service
public class UserServiceImpl implements UserService {
    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository repository;

    public User save(User user) {
        LOG.info("save user " + user);
        return repository.save(user);
    }

    public void delete(int id) {
        LOG.info("delete user " + id);
        ExceptionUtil.checkNotFoundWithId(repository.delete(id), id);
    }

    public User get(int id) throws NotFoundException {
        LOG.info("get user " + id);
        return ExceptionUtil.checkNotFoundWithId(repository.get(id), id);
    }

    public User getByEmail(String email) throws NotFoundException {
        LOG.info("user getByEmail " + email);
        return ExceptionUtil.checkNotFound(repository.getByEmail(email), "email=" + email);
    }

    public List<User> getAll() {
        LOG.info("user getAll");
        return repository.getAll();
    }

    public void update(User user) {
        LOG.info(" update user");
        repository.save(user);
    }
}
