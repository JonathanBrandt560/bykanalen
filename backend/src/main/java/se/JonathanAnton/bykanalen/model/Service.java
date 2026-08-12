package se.JonathanAnton.bykanalen.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "services")
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String description;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] image;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime publishDate;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private GroupInfo groupInfo;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Service() {}

    public Service(String title, String description, byte[] image, GroupInfo groupInfo, User user) {
        this.title = title;
        this.description = description;
        this.image = image;
        this.groupInfo = groupInfo;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public LocalDateTime getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(LocalDateTime publishDate) {
        this.publishDate = publishDate;
    }

    public GroupInfo getGroup() {
        return groupInfo;
    }

    public void setGroup(GroupInfo groupInfo) {
        this.groupInfo = groupInfo;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
