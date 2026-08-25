package se.JonathanAnton.bykanalen.dto;
import java.time.LocalDateTime;

/** DTO för list-vy av evenemang */
public class EventSummaryDTO {
    private Long id;
    private String title;
    private byte[] image;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public EventSummaryDTO() {}

    public EventSummaryDTO(Long id, String title, byte[] image, LocalDateTime startDate, LocalDateTime endDate) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Long getId() {return id;}

    public String getTitle() {
        return title;
    }

    public byte[] getImage() {
        return image;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }
}
