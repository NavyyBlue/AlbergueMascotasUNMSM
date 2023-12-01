package org.grupo12.models;

import lombok.Data;

@Data
public class News {
    private int newsId;
    private String title;
    private String description;
    private String image;
    private String creationDate;
    private int userId;
    private int newsType;
    private int active;

}
