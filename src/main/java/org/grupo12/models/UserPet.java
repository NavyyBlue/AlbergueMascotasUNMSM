package org.grupo12.models;

import lombok.Data;

@Data
public class UserPet {
    private int userPetId;
    private int userId;
    private int petId;
    private String petName;
    private String adoptionDate;
    private int type;
    private boolean isFavorite;
    private boolean active;
}
