package ru.itmo.webmail.model.service;

import com.google.common.hash.Hashing;
import ru.itmo.webmail.model.repository.EmailConfirmationRepository;
import ru.itmo.webmail.model.repository.impl.EmailConfirmationRepositoryImpl;

import java.nio.charset.StandardCharsets;

/**
 * ru.itmo.webmail.model.service
 * Short Description: (눈_눈)
 *
 * @author nikitos
 * @version 1.0.0
 */

public class EmailConfirmationService {

    private EmailConfirmationRepository emailConfirmationRepository = new EmailConfirmationRepositoryImpl();
//    private final String USER_SECRET_SALT = "ALPHA777";

    public String getSecret(Long userId) {
        return emailConfirmationRepository.getSecret(userId);
    }

    public void save(Long userId, String secret) {
        emailConfirmationRepository.save(userId, secret);
    }

    public String getStringSha(String password) {
        return Hashing.sha256().hashString(password,
                StandardCharsets.UTF_8).toString();
    }

}
