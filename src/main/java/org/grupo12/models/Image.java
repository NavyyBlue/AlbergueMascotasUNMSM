package org.grupo12.models;

import lombok.Data;

@Data
public class Image {
    private int imageId;
    private int petId;
    private int productId;
    private String imageUrl;
    private boolean active;
    private boolean isMainImage;
}
