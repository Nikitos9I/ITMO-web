package ru.itmo.webmail.model.service;

import ru.itmo.webmail.model.domain.Article;
import ru.itmo.webmail.model.exception.ValidationException;
import ru.itmo.webmail.model.repository.ArticleRepository;
import ru.itmo.webmail.model.repository.impl.ArticleRepositoryImpl;

import java.util.List;

/**
 * ru.itmo.webmail.model.service
 * Short Description: (눈_눈)
 *
 * @author nikitos
 * @version 1.0.0
 */

public class ArticleService {

    private ArticleRepository articleRepository = new ArticleRepositoryImpl();

    public void saveArticle(Article article) {
        articleRepository.save(article);
    }

    public void isValid(String text) throws ValidationException {
        if (text.isEmpty())
            throw new ValidationException("Here should be more then 0 symbols");
    }

    public List<Article> findAllArticles() {
        return articleRepository.findArticles();
    }

    public List<Article> findArticlesByUserId(Long userId) {
        return articleRepository.findArticlesByUserId(userId);
    }

    public Article findArticleById(Long articleId) {
        return articleRepository.findById(articleId);
    }

    public void updateHiddenStatus(Long articleId, Boolean value) {
        articleRepository.updateHiddenStatus(articleId, value);
    }

}
