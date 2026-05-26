package se.JonathanAnton.bykanalen.dto;

import java.time.LocalDateTime;

public class ServiceDTO {

    private Long id;
    private String title;
    private String description;
    private byte[] image;
    private LocalDateTime publishDate;

    public ServiceDTO() {}

    public ServiceDTO(Long id, String title, String description, byte[] image, LocalDateTime publishDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.image = image;
        this.publishDate = publishDate;
    }

    public Long getId() {
        return id;
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
}
