package se.JonathanAnton.bykanalen.dto;

import java.time.LocalDateTime;

public class EventDetailDTO {

    private String title;
    private byte[] image;
    private String description;
    private LocalDateTime publishDate;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime closeRegistrationDate;
    private Long groupId;
    private long registrationCount;

    public EventDetailDTO() {}

    public EventDetailDTO(String title, byte[] image, String description, LocalDateTime publishDate, LocalDateTime startDate, LocalDateTime endDate, LocalDateTime closeRegistrationDate, Long groupId, long registrationCount) {
        this.title = title;
        this.image = image;
        this.description = description;
        this.publishDate = publishDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.closeRegistrationDate = closeRegistrationDate;
        this.groupId = groupId;
        this.registrationCount = registrationCount;
    }

    public String getTitle() {
        return title;
    }

    public byte[] getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getPublishDate() {
        return publishDate;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public LocalDateTime getCloseRegistrationDate() {
        return closeRegistrationDate;
    }

    public Long getGroupId() {
        return groupId;
    }

    public long getRegistrationCount() {return registrationCount;}
}
