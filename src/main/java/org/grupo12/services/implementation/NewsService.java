package org.grupo12.services.implementation;

import jakarta.servlet.http.HttpServletRequest;
import org.grupo12.dao.NewsDAO;
import org.grupo12.models.News;
import org.grupo12.services.interfaces.INewsService;
import org.grupo12.util.Pagination;

import java.util.List;

public class NewsService implements INewsService {
    private final NewsDAO newsDAO;

    public NewsService(NewsDAO newsDAO) {
        this.newsDAO = newsDAO;
    }
    public List<News> getNewsPaginated(HttpServletRequest request){
        int newsId = 0;
        int active=1;
        String tittle = "";
        String description = "";
        String searchKeyword = "";

        String newsIdParam = request.getParameter("newsId");
        if (newsIdParam != null && !newsIdParam.isEmpty())
            newsId = Integer.parseInt(newsIdParam);

        String titleParam = request.getParameter("title");
        if (titleParam != null && !titleParam.isEmpty()) {
            tittle = String.join(titleParam);
        }

        String descriptionParam = request.getParameter("description");
        if (descriptionParam != null && !descriptionParam.isEmpty()) {
            description = String.join(descriptionParam);
        }

        searchKeyword = request.getParameter("searchKeyword");

        Pagination pagination = new Pagination();

        // Retrieve the requested page number from the request
        String pageParam = request.getParameter("page");
        int requestedPage = 1; // Default to page 1
        if (pageParam != null && !pageParam.isEmpty()) {
            requestedPage = Integer.parseInt(pageParam);
        }

        int total = newsDAO.getTotalNewsCount(searchKeyword);
        pagination.setTotal(total);
        pagination.calculate();
        pagination.setCurrentPage(requestedPage);

        int offset = pagination.calculateOffset(requestedPage);
        int limit = pagination.getLimit();

        request.setAttribute("pagination", pagination);

        return newsDAO.getNews(newsId,active, offset, limit);
    }


    public boolean deleteNews(int newsId) {return newsDAO.deleteNews(newsId);}
    public boolean restoreNews(int newsId) {return newsDAO.restoreNews(newsId);}
    public boolean updateNews(News updatedNews) {
        return newsDAO.updateNews(updatedNews);
    }

    public boolean insertNews(News insertNews){return  newsDAO.insertNews(insertNews);}

}
