package se.JonathanAnton.bykanalen.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public class CreateServiceDTO {

    @NotBlank(message = "Titel får inte vara tom")
    private String title;

    private String description;

    private byte[] image;

    private LocalDateTime publishDate;

    public CreateServiceDTO() {}

    public CreateServiceDTO(String title, String description, byte[] image, LocalDateTime publishDate) {
        this.title = title;
        this.description = description;
        this.image = image;
        this.publishDate = publishDate;
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
