package se.JonathanAnton.bykanalen.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class CreateServiceDTO {

    @NotBlank(message = "Titel får inte vara tom")
    @Size(max = 100, message = "Titel får inte vara längre än 100 tecken")
    private String title;

    @Size(max = 500, message = "Beskrivning får inte vara mer än 500 tecken")
    private String description;

    private byte[] image;

    private LocalDateTime publishDate;

    public CreateServiceDTO() {}

    public CreateServiceDTO(String title, String description, byte[] image) {
        this.title = title;
        this.description = description;
        this.image = image;
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

}
