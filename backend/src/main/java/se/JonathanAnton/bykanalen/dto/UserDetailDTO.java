package se.JonathanAnton.bykanalen.dto;

import se.JonathanAnton.bykanalen.enums.Type;

import java.time.LocalDateTime;

public class UserDetailDTO {

    private Long userId;
    private Type type;
    private boolean isSuspended;
    private LocalDateTime registrationDate;

    public UserDetailDTO() {}

    public UserDetailDTO(Long userId, Type type, boolean isSuspended, LocalDateTime registrationDate) {
        this.userId = userId;
        this.type = type;
        this.isSuspended = isSuspended;
        this.registrationDate = registrationDate;
    }

    public Long getUserId() {
        return userId;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public boolean isSuspended() {
        return isSuspended;
    }

    public void setSuspended(boolean suspended) {
        isSuspended = suspended;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }
}


