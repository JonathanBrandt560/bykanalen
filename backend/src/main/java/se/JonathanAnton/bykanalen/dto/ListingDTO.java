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
    private long userId;

    public ListingDTO() {}

    public ListingDTO(Long id, String title, String description, byte[] image, LocalDateTime publishDate, Integer price, String location, long userId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.image = image;
        this.publishDate = publishDate;
        this.price = price;
        this.location = location;
        this.userId = userId;
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

    public long getUserId() {
        return userId;
    }
}
