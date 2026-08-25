package se.JonathanAnton.bykanalen.model;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/** Entitetsklass för evenemang (events) */
@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] image;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime publishDate;

    @Column(nullable = false)
    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private LocalDateTime closeRegistrationDate;

    // Ett evenemang kan tillhöra en grupp. En grupp kan ha många evenemang
    @ManyToOne
    @JoinColumn(name = "group_id")
    private GroupInfo groupInfo;

    // Ett evenemang kan ha många användarregistreringar. En användarregistrering kan tillhöra ett evenemang.
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EventRegistration> registrations = new ArrayList<>();

    public Event() {}

    public Event(String title, byte[] image, String description, LocalDateTime publishDate, LocalDateTime startDate, LocalDateTime endDate, LocalDateTime closeRegistrationDate, GroupInfo groupInfo) {
        this.title = title;
        this.image = image;
        this.description = description;
        this.publishDate = publishDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.closeRegistrationDate = closeRegistrationDate;
        this.groupInfo = groupInfo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDateTime getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(LocalDateTime publishDate) {
        this.publishDate = publishDate;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public LocalDateTime getCloseRegistrationDate() {
        return closeRegistrationDate;
    }

    public void setCloseRegistrationDate(LocalDateTime closeRegistrationDate) {
        this.closeRegistrationDate = closeRegistrationDate;
    }

    public List<EventRegistration> getRegistrations() {
        return registrations;
    }

    public void setRegistrations(List<EventRegistration> registrations) {
        this.registrations = registrations;
    }

    public GroupInfo getGroup() {
        return groupInfo;
    }

    public void setGroup(GroupInfo groupInfo) {
        this.groupInfo = groupInfo;
    }
}
