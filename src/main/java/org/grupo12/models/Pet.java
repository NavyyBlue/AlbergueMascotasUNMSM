package org.grupo12.models;
import lombok.Data;

@Data
public class Pet {
    private int petId;
    private String name;
    private int age;
    private String speciesId;
    private String gender;
    private String description;
    private String breed;
    private String location;
    private String entryDate;
    private int adoptionStatusId;
    private String imageUrl;
    private String petStatusName;
    private int active;
}
