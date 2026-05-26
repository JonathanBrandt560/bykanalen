package se.JonathanAnton.bykanalen.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public class CreateEventDTO {

    @NotBlank(message = "Titel får inte vara tom")
    private String title;

    private byte[] image;

    private String description;

    private LocalDateTime publishDate;

    @Future(message = "Startdatum måste vara i framtiden")
    private LocalDateTime startDate;

    @Future(message = "Slutdatum måste vara i framtiden")
    private LocalDateTime endDate;

    @Future(message = "Sista anmälningsdag måste vara i framtiden")
    private LocalDateTime closeRegistrationDate;

    public CreateEventDTO() {}

    public CreateEventDTO(String title, byte[] image, String description, LocalDateTime publishDate, LocalDateTime startDate, LocalDateTime endDate, LocalDateTime closeRegistrationDate) {
        this.title = title;
        this.image = image;
        this.description = description;
        this.publishDate = publishDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.closeRegistrationDate = closeRegistrationDate;
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
