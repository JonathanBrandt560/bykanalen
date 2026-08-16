package se.JonathanAnton.bykanalen.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/** DTO för skapande av ett nytt allmänt inlägg */
public class CreateGeneralPostDTO {

    @NotBlank(message = "Titel får inte vara tom")
    @Size(max = 100, message = "Titel får inte vara längre än 100 tecken")
    private String title;

    private byte[] image;

    private String description;

    public CreateGeneralPostDTO() {}

    public CreateGeneralPostDTO(String title, byte[] image, String description) {
        this.title = title;
        this.image = image;
        this.description = description;
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
}
