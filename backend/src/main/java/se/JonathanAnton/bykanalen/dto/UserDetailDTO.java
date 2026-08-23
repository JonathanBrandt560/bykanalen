package se.JonathanAnton.bykanalen.dto;

import se.JonathanAnton.bykanalen.enums.UserType;
import java.time.LocalDateTime;

public class UserDetailDTO {

    private Long userId;
    private String username;
    private UserType type;
    private boolean isSuspended;
    private LocalDateTime registrationDate;

    public UserDetailDTO() {}

    public UserDetailDTO(Long userId, String username, UserType type, boolean isSuspended, LocalDateTime registrationDate) {
        this.userId = userId;
        this.username = username;
        this.type = type;
        this.isSuspended = isSuspended;
        this.registrationDate = registrationDate;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public UserType getType() {
        return type;
    }

    public boolean isSuspended() {
        return isSuspended;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }
}