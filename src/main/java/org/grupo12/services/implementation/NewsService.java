package org.grupo12.services.implementation;

import org.grupo12.dao.NewsDAO;
import org.grupo12.services.interfaces.INewsService;

public class NewsService implements INewsService {
    private final NewsDAO newsDAO;

    public NewsService(NewsDAO newsDAO) {
        this.newsDAO = newsDAO;
    }


}
