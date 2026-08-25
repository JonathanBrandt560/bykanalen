package se.JonathanAnton.bykanalen.model;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import se.JonathanAnton.bykanalen.enums.UserType;
import java.time.LocalDateTime;

/** Entitetsklass för användardetaljer (userdetails)
 * 1-1-relation mellan användare (user) och användardetaljer
 */
@Entity
@Table(name = "user_details")
public class UserDetail {

    @Id
    private Long userId;

    // En användardetaljer kan tillhöra en användare. En användare ha en användardetaljer
    @OneToOne
    @JoinColumn(name = "user_id")
    @MapsId // Säger att userId är både PK och FK
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserType type;

    @Column(nullable = false)
    private boolean isSuspended;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime registrationDate;

    public UserDetail() {}

    public UserDetail(User user, UserType type, boolean isSuspended) {
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

    public User getUser() {
        return user;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
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
