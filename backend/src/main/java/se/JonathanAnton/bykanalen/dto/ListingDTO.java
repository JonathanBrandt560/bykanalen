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
    private Long userId;

    public ListingDTO(Long id, String title, String description, byte[] image, LocalDateTime publishDate, Integer price, String location, Long user) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.image = image;
        this.publishDate = publishDate;
        this.price = price;
        this.location = location;
        this.userId = user;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public byte[] getImage() {
        return image;
    }

    public LocalDateTime getPublishDate() {
        return publishDate;
    }

    public Integer getPrice() {
        return price;
    }

    public String getLocation() {
        return location;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
