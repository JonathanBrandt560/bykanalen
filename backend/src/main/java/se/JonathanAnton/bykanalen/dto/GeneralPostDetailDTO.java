package se.JonathanAnton.bykanalen.dto;

import java.time.LocalDateTime;

public class GeneralPostDetailDTO {

    private String title;
    private byte[] image;
    private LocalDateTime publishDate;
    private String description;
    private int likeCount;
    private Long userId;
    private Long groupId;
    private String username;

    public GeneralPostDetailDTO() {}

    public GeneralPostDetailDTO(String title, byte[] image, LocalDateTime publishDate, String description, int likeCount, Long userId, Long groupId, String username) {
        this.title = title;
        this.image = image;
        this.publishDate = publishDate;
        this.description = description;
        this.likeCount = likeCount;
        this.userId = userId;
        this.groupId = groupId;
        this.username = username;
    }

    public String getTitle() {
        return title;
    }

    public byte[] getImage() {
        return image;
    }

    public LocalDateTime getPublishDate() {
        return publishDate;
    }

    public String getDescription() {
        return description;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public String getUsername() {return username;}
}
