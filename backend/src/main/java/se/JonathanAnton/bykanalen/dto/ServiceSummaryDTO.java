package se.JonathanAnton.bykanalen.dto;

import java.time.LocalDateTime;

public class ServiceSummaryDTO {

    private Long id;
    private String title;
    private byte[] image;
    private LocalDateTime publishDate;
    private String username;

    public ServiceSummaryDTO() {}

    public ServiceSummaryDTO(Long id, String title, byte[] image, LocalDateTime publishDate, String username) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.publishDate = publishDate;
        this.username = username;
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
