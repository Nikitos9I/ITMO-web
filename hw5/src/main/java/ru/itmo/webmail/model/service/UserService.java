package ru.itmo.webmail.model.service;

import com.google.common.hash.Hashing;
import ru.itmo.webmail.model.domain.User;
import ru.itmo.webmail.model.exception.ValidationException;
import ru.itmo.webmail.model.repository.UserRepository;
import ru.itmo.webmail.model.repository.impl.UserRepositoryImpl;

import java.nio.charset.StandardCharsets;
import java.util.List;

@SuppressWarnings("UnstableApiUsage")
public class UserService {
    private static final String USER_PASSWORD_SALT = "dc3475f2b301851b";
    private static final String EMAIL_REGEXP = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

    private UserRepository userRepository = new UserRepositoryImpl();

    public void validateRegistration(User user, String password) throws ValidationException {
        loginValidation(user.getLogin());
        passwordValidation(password);
        emailValidation(user.getEmail());
    }

    public void register(User user, String password) {
        String passwordSha = getPasswordSha(password);
        userRepository.save(user, passwordSha);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void validateEnter(String loginOrEmail, String password) throws ValidationException {
        User user;
        if (loginOrEmail.contains("@")) {
            user = userRepository.findByEmail(loginOrEmail);
        } else {
            user = userRepository.findByLogin(loginOrEmail);
        }

        if (user == null) {
            throw new ValidationException("Invalid login or email");
        }

        if (!user.isConfirmed()) {
            throw new ValidationException("You haven't confirmed you email yet");
        }

        passwordValidation(password);

        if (userRepository.findByLoginAndPasswordSha(user.getLogin(), getPasswordSha(password)) == null) {
            throw new ValidationException("Invalid password");
        }
    }

    public void updateConfirmStatus(Long userId, boolean value) {
        userRepository.updateConfirmStatus(userId, value);
    }

    public User findByLoginOrPassword(String loginOrPassword) {
        User user = userRepository.findByEmail(loginOrPassword);
        if (user == null) {
            user = userRepository.findByLogin(loginOrPassword);
        }

        return user;
    }

    private String getPasswordSha(String password) {
        return Hashing.sha256().hashString(USER_PASSWORD_SALT + password,
                StandardCharsets.UTF_8).toString();
    }

    public User authorize(String login, String password) {
        if (login.contains("@"))
            return userRepository.findByEmailAndPasswordSha(login, getPasswordSha(password));
        else
            return userRepository.findByLoginAndPasswordSha(login, getPasswordSha(password));
    }

    public User find(long userId) {
        return userRepository.find(userId);
    }

    /*----------- VALIDATION METHODS -----------*/

    private void loginValidation(String login) throws ValidationException {
        if (userRepository.findByLogin(login) != null) {
            throw new ValidationException("Login is already in use");
        }
        if (login == null || login.isEmpty()) {
            throw new ValidationException("Login is required");
        }
        if (!login.matches("[a-zA-Z]+")) {
            throw new ValidationException("Login can contain only Latin letters");
        }
        if (login.length() > 15) {
            throw new ValidationException("Login can't be longer than 15");
        }
        if (login.length() < 3) {
            throw new ValidationException("Login can't be shorter than 3");
        }
    }

    private void passwordValidation(String password) throws ValidationException {
        if (password == null || password.isEmpty()) {
            throw new ValidationException("Password is required");
        }
        if (password.length() < 4) {
            throw new ValidationException("Password can't be shorter than 4");
        }
        if (password.length() > 32) {
            throw new ValidationException("Password can't be longer than 32");
        }
    }

    private void passwordsEquals(String password, String confirmPassword) throws ValidationException {
        if (!password.equals(confirmPassword)) {
            throw new ValidationException("Passwords don't match");
        }
    }

    private void emailValidation(String email) throws ValidationException {
        if (userRepository.findByEmail(email) != null) {
            throw new ValidationException("Email is already in use");
        }
        if (!email.matches(EMAIL_REGEXP)) {
            throw new ValidationException("Not valid email");
        }
    }
}
