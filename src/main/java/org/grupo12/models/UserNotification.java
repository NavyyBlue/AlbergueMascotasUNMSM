package org.grupo12.models;

import lombok.Data;

@Data
public class UserNotification {
    private int userNotificationId;
    private int userId;
    private int notificationId;
    private boolean viewed;
    private boolean active;
}
