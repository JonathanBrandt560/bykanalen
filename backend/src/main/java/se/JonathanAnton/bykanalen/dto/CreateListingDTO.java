package se.JonathanAnton.bykanalen.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDateTime;

public class CreateListingDTO {

    @NotBlank(message = "Titel får inte vara tom")
    private String title;

    private String description;

    private byte[] image;

    @NotNull(message = "Pris måste anges")
    @PositiveOrZero(message = "Pris får inte vara negativt")
    private Integer price;

    private String location;

    public CreateListingDTO() {}

    public CreateListingDTO(String title, String description, byte[] image, Integer price, String location) {
        this.title = title;
        this.description = description;
        this.image = image;
        this.price = price;
        this.location = location;
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

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
