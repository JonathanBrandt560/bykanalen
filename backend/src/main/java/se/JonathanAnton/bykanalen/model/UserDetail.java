package se.JonathanAnton.bykanalen.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
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

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private Byte age;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private boolean isSuspended;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime registrationDate;

    public UserDetail() {}

    public UserDetail(User user, String email, Byte age, String firstName, String lastName, boolean isSuspended) {
        this.user = user;
        this.email = email;
        this.age = age;
        this.firstName = firstName;
        this.lastName = lastName;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Byte getAge() {
        return age;
    }

    public void setAge(Byte age) {
        this.age = age;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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
