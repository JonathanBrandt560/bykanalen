package se.JonathanAnton.bykanalen.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDateTime;

public class CreateGeneralPostDTO {

    @NotBlank(message = "Titel får inte vara tom")
    private String title;

    private byte[] image;

    private LocalDateTime publishDate;

    private String description;

    @PositiveOrZero(message = "Antalet gillningar får inte vara negativt")
    private int likeCount;

    public CreateGeneralPostDTO() {}

    public CreateGeneralPostDTO(String title, byte[] image, LocalDateTime publishDate, String description, int likeCount) {
        this.title = title;
        this.image = image;
        this.publishDate = publishDate;
        this.description = description;
        this.likeCount = likeCount;
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
}
