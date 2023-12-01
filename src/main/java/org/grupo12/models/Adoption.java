package org.grupo12.models;

import lombok.Data;

@Data
public class Adoption {
    private int userPetId;
    private int userId;
    private int petId;
    private String petName;
    private String userFullName;
    private String adoptionDate;
    private int statusId;
}
