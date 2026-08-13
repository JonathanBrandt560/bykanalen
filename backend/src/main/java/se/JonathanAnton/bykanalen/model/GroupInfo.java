package se.JonathanAnton.bykanalen.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "group_infos")
public class GroupInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String groupName;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String text1;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String text2;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String text3;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] image1;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] image2;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] image3;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime createdDate;

    @OneToMany(mappedBy = "groupInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MemberlistGroup> memberlistGroups = new ArrayList<>();

    @OneToMany(mappedBy = "groupInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Event> events = new ArrayList<>();

    @OneToMany(mappedBy = "groupInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GeneralPost> generalPosts = new ArrayList<>();

    @OneToMany(mappedBy = "groupInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Service> services = new ArrayList<>();

    public GroupInfo() {}

    public GroupInfo(String groupName, String text1, String text2, String text3, byte[] image1, byte[] image2, byte[] image3) {
        this.groupName = groupName;
        this.text1 = text1;
        this.text2 = text2;
        this.text3 = text3;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getText1() {
        return text1;
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }

    public String getText2() {
        return text2;
    }

    public void setText2(String text2) {
        this.text2 = text2;
    }

    public String getText3() {
        return text3;
    }

    public void setText3(String text3) {
        this.text3 = text3;
    }

    public byte[] getImage1() {
        return image1;
    }

    public void setImage1(byte[] image1) {
        this.image1 = image1;
    }

    public byte[] getImage2() {
        return image2;
    }

    public void setImage2(byte[] image2) {
        this.image2 = image2;
    }

    public byte[] getImage3() {
        return image3;
    }

    public void setImage3(byte[] image3) {
        this.image3 = image3;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public List<MemberlistGroup> getMemberlistGroups() {
        return memberlistGroups;
    }

    public void setMemberlistGroups(List<MemberlistGroup> memberlistGroups) {
        this.memberlistGroups = memberlistGroups;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public List<GeneralPost> getGeneralPosts() {
        return generalPosts;
    }

    public void setGeneralPosts(List<GeneralPost> generalPosts) {
        this.generalPosts = generalPosts;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }
}
