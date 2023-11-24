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
    private Date entryDate;
    private int adoptionStatusId;
    private String imageUrl;
    private String petStatusName;
    private int active;
}
