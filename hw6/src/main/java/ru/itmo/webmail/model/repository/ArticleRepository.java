package ru.itmo.webmail.model.repository;

import ru.itmo.webmail.model.domain.Article;

import java.util.List;

public interface ArticleRepository {
    void save(Article article);
    void updateHiddenStatus(Long articleId, boolean value);
    Article findById(Long id);
    List<Article> findArticles();
    List<Article> findArticlesByUserId(Long userId);
}
