package se.JonathanAnton.bykanalen.dto;

import java.time.LocalDateTime;

public class ServiceDetailDTO {

    private String title;
    private String description;
    private byte[] image;
    private LocalDateTime publishDate;
    private String username;

    public ServiceDetailDTO(String title, String description, byte[] image, LocalDateTime publishDate, String username) {
        this.title = title;
        this.description = description;
        this.image = image;
        this.publishDate = publishDate;
        this.username = username;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
