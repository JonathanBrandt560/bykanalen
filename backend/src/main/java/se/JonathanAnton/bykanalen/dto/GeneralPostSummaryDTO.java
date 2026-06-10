package se.JonathanAnton.bykanalen.dto;

import java.time.LocalDateTime;

public class GeneralPostSummaryDTO {

    private Long id;
    private String title;
    private LocalDateTime publishDate;
    private int likeCount;

    public GeneralPostSummaryDTO() {}

    public GeneralPostSummaryDTO(Long id, String title, LocalDateTime publishDate, int likeCount) {
        this.id = id;
        this.title = title;
        this.publishDate = publishDate;
        this.likeCount = likeCount;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getPublishDate() {
        return publishDate;
    }

    public int getLikeCount() {
        return likeCount;
    }
}
