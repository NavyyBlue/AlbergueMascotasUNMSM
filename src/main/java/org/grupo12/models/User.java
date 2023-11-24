package org.grupo12.models;

import lombok.Data;

@Data
public class User {
    private int userId;
    private String firstName;
    private String lastName;
    private String email;
    private String userName;
    private String password;
    private String phoneNumber;
    private String userImage;
    private int userRole;
    private boolean active;
    private int userRole;
}
