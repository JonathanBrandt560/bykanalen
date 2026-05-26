package se.JonathanAnton.bykanalen.dto;

import java.time.LocalDateTime;

public class GeneralPostDTO {

    private Long id;
    private String title;
    private byte[] image;
    private LocalDateTime publishDate;
    private String description;
    private int likeCount;

    public GeneralPostDTO() {}

    public GeneralPostDTO(Long id, String title, byte[] image, LocalDateTime publishDate, String description, int likeCount) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.publishDate = publishDate;
        this.description = description;
        this.likeCount = likeCount;
    }

    public Long getId() {return id;}

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
}
