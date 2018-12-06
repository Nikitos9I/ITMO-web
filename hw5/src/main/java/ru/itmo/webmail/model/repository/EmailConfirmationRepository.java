package ru.itmo.webmail.model.repository;

public interface EmailConfirmationRepository {
    void save(Long userId, String secret);
    String getSecret(Long userId);
}
