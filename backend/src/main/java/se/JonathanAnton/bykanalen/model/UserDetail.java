package se.JonathanAnton.bykanalen.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import se.JonathanAnton.bykanalen.enums.Type;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_details")
public class UserDetail {

    @Id
    private Long userId;

    @OneToOne
    @JoinColumn(name = "user_id")
    @MapsId // Säger att userId är både PK och FK
    private User user;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Type type;

    @Column(nullable = false)
    private boolean isSuspended;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime registrationDate;

    public UserDetail() {}

    public UserDetail(User user, Type type, boolean isSuspended) {
        this.user = user;
        this.type = type;
        this.isSuspended = isSuspended;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public User getUser() {
        return user;
    }
}
