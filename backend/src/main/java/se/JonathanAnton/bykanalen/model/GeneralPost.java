package se.JonathanAnton.bykanalen.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;


@Entity
@Table(name = "general_posts")
public class GeneralPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] image;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime publishDate;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "like_count", nullable = false)
    private int likeCount;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public GeneralPost() {}

    public GeneralPost(String title, byte[] image, LocalDateTime publishDate, String description, int likeCount, User user) {
        this.title = title;
        this.image = image;
        this.publishDate = publishDate;
        this.description = description;
        this.likeCount = likeCount;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
