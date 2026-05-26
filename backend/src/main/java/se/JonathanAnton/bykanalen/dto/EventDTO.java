package se.JonathanAnton.bykanalen.dto;

import java.time.LocalDateTime;

public class EventDTO {

    private Long id;
    private String title;
    private byte[] image;
    private String description;
    private LocalDateTime publishDate;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime closeRegistrationDate;

    public EventDTO() {}

    public EventDTO(Long id, String title, byte[] image, String description, LocalDateTime publishDate, LocalDateTime startDate, LocalDateTime endDate, LocalDateTime closeRegistrationDate) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.description = description;
        this.publishDate = publishDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.closeRegistrationDate = closeRegistrationDate;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(LocalDateTime publishDate) {
        this.publishDate = publishDate;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public LocalDateTime getCloseRegistrationDate() {
        return closeRegistrationDate;
    }

    public void setCloseRegistrationDate(LocalDateTime closeRegistrationDate) {
        this.closeRegistrationDate = closeRegistrationDate;
    }
}
