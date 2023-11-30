package org.grupo12.models;
import lombok.Data;

import java.util.Date;

@Data
public class Pet {
    private int petId;
    private String name;
    private int age;
    private int speciesId;
    private String gender;
    private String description;
    private String breed;
    private int location;
    private String entryDate;
    private int adoptionStatusId;
    private String imageUrl;
    private boolean isMainImage;
    private boolean isImageActive;
    private String petStatusName;
    private int active;
    private int totalFavorites;
}
