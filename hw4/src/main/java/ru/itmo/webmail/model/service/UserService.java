package ru.itmo.webmail.model.service;

import com.google.common.hash.Hashing;
import ru.itmo.webmail.model.domain.User;
import ru.itmo.webmail.model.exception.ValidationException;
import ru.itmo.webmail.model.repository.UserRepository;
import ru.itmo.webmail.model.repository.impl.UserRepositoryImpl;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class UserService {
    private static final String USER_PASSWORD_SALT = "dc3475f2b301851b";
    private static final String EMAIL_REGEXP = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

    private UserRepository userRepository = new UserRepositoryImpl();

    public void validateRegistration(User user, String password, String confirmPassword) throws ValidationException {
        loginValidation(user.getLogin());
        loginInUse(user.getLogin());
        loginLength(user.getLogin());
        passwordValidation(password);
        passwordLength(password);
        passwordsEquals(password, confirmPassword);
        emailValidation(user.getEmail());
        emailInUse(user.getEmail());
    }

    public void validateAuthorization(String login, String password) throws ValidationException {
        if (login.contains("@")) {
            emailValidation(login);
        } else {
            loginValidation(login);
            loginLength(login);
        }

        passwordValidation(password);
    }

    public void register(User user, String password) {
        user.setId((long) findAll().size() + 1);
        user.setPasswordSha1(hashPassword(password));
        userRepository.save(user);
    }

    public User auth(String login, String password) throws ValidationException {
        User user = userRepository.findByLogin(login);

        if (user == null) {
            user = userRepository.findByEmail(login);
        }

        if (user == null) {
            throw new ValidationException("Wrong login");
        }

        if (!user.getPasswordSha1().equals(hashPassword(password))) {
            throw new ValidationException("Wrong password");
        }

        return user;
    }

    private String hashPassword(String password) {
        return Hashing.sha256().hashString(USER_PASSWORD_SALT + password, StandardCharsets.UTF_8).toString();
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Long findCount() {
        return userRepository.findCount();
    }

    public User findById(Long id) {
        return userRepository.findById(id);
    }

    /*----------- VALIDATION METHODS -----------*/

    private void loginLength(String login) throws ValidationException {
        if (login.length() > 10) {
            throw new ValidationException("Login can't be longer than 8");
        }
        if (login.length() < 3) {
            throw new ValidationException("Login can't be shorter than 3");
        }
    }

    private void passwordLength(String password) throws ValidationException {
        if (password.length() < 4) {
            throw new ValidationException("Password can't be shorter than 4");
        }
        if (password.length() > 32) {
            throw new ValidationException("Password can't be longer than 32");
        }
    }

    private void loginValidation(String login) throws ValidationException {
        if (login == null || login.isEmpty()) {
            throw new ValidationException("Login is required");
        }
        if (!login.matches("[a-zA-Z]+")) {
            throw new ValidationException("Login can contain only Latin letters");
        }
    }

    private void loginInUse(String login) throws ValidationException {
        if (userRepository.findByLogin(login) != null) {
            throw new ValidationException("Login is already in use");
        }
    }

    private void passwordValidation(String password) throws ValidationException {
        if (password == null || password.isEmpty()) {
            throw new ValidationException("Password is required");
        }
    }

    private void passwordsEquals(String password, String confirmPassword) throws ValidationException {
        if (!password.equals(confirmPassword)) {
            throw new ValidationException("Passwords don't match");
        }
    }

    private void emailValidation(String email) throws ValidationException {
        if (!email.matches(EMAIL_REGEXP)) {
            throw new ValidationException("Not valid email");
        }
    }

    private void emailInUse(String email) throws ValidationException {
        if (userRepository.findByEmail(email) != null) {
            throw new ValidationException("Email is already in use");
        }
    }

}
