package ru.itmo.webmail.model.repository;

import ru.itmo.webmail.model.domain.User;

import java.util.List;

public interface UserRepository {
    User find(long userId);
    User findByLogin(String login);
    User findByEmail(String email);
    User findByLoginAndPasswordSha(String login, String passwordSha);
    User findByEmailAndPasswordSha(String email, String passwordSha);
    List<User> findAll();
    void save(User user, String passwordSha);
    void updateConfirmStatus(Long userId, boolean value);
}
