package ru.itmo.webmail.model.service;

import ru.itmo.webmail.model.domain.News;
import ru.itmo.webmail.model.repository.NewsRepository;
import ru.itmo.webmail.model.repository.impl.NewsRepositoryImpl;

import java.util.List;

/**
 * ru.itmo.webmail.model.service
 * Short Description: (눈_눈)
 *
 * @author nikitos
 * @version 1.0.0
 */

public class NewsService {

    private NewsRepository newsRepository = new NewsRepositoryImpl();
    private UserService userService = new UserService();

    public void save(News news) {
        news.setUserLogin(userService.findById(news.getUserId()).getLogin());
        newsRepository.save(news);
    }

    public List<News> findAll() {
        return newsRepository.findAll();
    }
}
