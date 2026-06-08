package se.JonathanAnton.bykanalen.dto;

import se.JonathanAnton.bykanalen.enums.Role;

import java.time.LocalDateTime;

public class UserDetailDTO {

    private Long userId;
    private Role type;
    private boolean isSuspended;
    private LocalDateTime registrationDate;

    public UserDetailDTO() {}

    public UserDetailDTO(Long userId, Role type, boolean isSuspended, LocalDateTime registrationDate) {
        this.userId = userId;
        this.type = type;
        this.isSuspended = isSuspended;
        this.registrationDate = registrationDate;
    }

    public Long getUserId() {
        return userId;
    }

    public Role getType() {
        return type;
    }

    public boolean isSuspended() {
        return isSuspended;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }
}


