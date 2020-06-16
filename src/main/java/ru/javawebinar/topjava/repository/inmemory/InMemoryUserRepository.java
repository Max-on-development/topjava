package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryUserRepository implements UserRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepository.class);
    private Map<Integer, User> userRepository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);


    public static final List<User> USERS = Arrays.asList(
            new User(null, "Max", "max@gmail.com", "123123", Role.USER, new Role[]{Role.USER, Role.ADMIN}),
            new User(null, "Vasya", "vasya@gmail.com", "123123", Role.USER, new Role[]{Role.USER})
    );
    {
        USERS.forEach(this::save);
    }

    @Override
    public User save(User user) {
        log.info("save {}", user);
        if (user.isNew()) {
            user.setId(counter.getAndIncrement());
            userRepository.put(user.getId(), user);
            return user;
        }
        // handle case: update, but not present in storage
        return userRepository.computeIfPresent(user.getId(), (id, oldUser) -> user);
    }

    @Override
    public boolean delete(int id) {
        log.info("delete {}", id);
        return userRepository.remove(id) != null;
    }

    @Override
    public User get(int id) {
        log.info("get {}", id);
        return userRepository.get(id);
    }

    @Override
    public List<User> getAll() {
        log.info("getAll");

        return userRepository.values().stream().sorted(
                Comparator.comparing(User::getName).thenComparing(User::getEmail)
        ).collect(Collectors.toList());
    }

    @Override
    public User getByEmail(String email) {
        log.info("getByEmail {}", email);
        return userRepository.values().stream().filter(user -> user.getEmail().equals(email)).findFirst().get();
    }
}
