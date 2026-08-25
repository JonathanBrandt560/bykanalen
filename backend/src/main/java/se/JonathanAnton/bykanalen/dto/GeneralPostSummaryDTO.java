package se.JonathanAnton.bykanalen.dto;
import java.time.LocalDateTime;

/** DTO för list-vy av ett allmänt inlägg */
public class GeneralPostSummaryDTO {

    private Long id;
    private String title;
    private LocalDateTime publishDate;
    private int likeCount;
    private boolean likedByCurrentUser;
    private String username;

    public GeneralPostSummaryDTO() {}

    public GeneralPostSummaryDTO(Long id, String title, LocalDateTime publishDate, int likeCount, String username) {
        this.id = id;
        this.title = title;
        this.publishDate = publishDate;
        this.likeCount = likeCount;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getPublishDate() {
        return publishDate;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public boolean isLikedByCurrentUser() {return likedByCurrentUser;}

    public void setLikedByCurrentUser(boolean likedByCurrentUser) {
        this.likedByCurrentUser = likedByCurrentUser;}

    public String getUsername() {
        return username;
    }
}
