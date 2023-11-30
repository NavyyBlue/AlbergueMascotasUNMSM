package org.grupo12.models;

import lombok.Data;

@Data
public class Product {
    private int productId;
    private String name;
    private double price;
    private int stock;
    private String description;
    private int category;
    private int active;
}
