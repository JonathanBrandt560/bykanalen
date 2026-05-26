package se.JonathanAnton.bykanalen.dto;

import java.time.LocalDateTime;

public class ListingDTO {

    private Long id;
    private String title;
    private String description;
    private byte[] image;
    private LocalDateTime publishDate;
    private Integer price;
    private String location;

    public ListingDTO() {}

    public ListingDTO(Long id, String title, String description, byte[] image, LocalDateTime publishDate, Integer price, String location) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.image = image;
        this.publishDate = publishDate;
        this.price = price;
        this.location = location;
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
