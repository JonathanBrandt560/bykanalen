package se.JonathanAnton.bykanalen.dto;
import java.time.LocalDateTime;

/** DTO för informationssida för specifik grupp/by */
public class GroupInfoDTO {

    private Long id;
    private String groupName;
    private String text1;
    private String text2;
    private String text3;
    private byte[] image1;
    private byte[] image2;
    private byte[] image3;
    private LocalDateTime createdDate;

    public GroupInfoDTO() {}

    public GroupInfoDTO(Long id, String groupName, String text1, String text2, String text3, byte[] image1, byte[] image2, byte[] image3, LocalDateTime createdDate) {
        this.id = id;
        this.groupName = groupName;
        this.text1 = text1;
        this.text2 = text2;
        this.text3 = text3;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
        this.createdDate = createdDate;
    }

    public Long getId() {
        return id;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getText1() {
        return text1;
    }

    public String getText2() {
        return text2;
    }

    public String getText3() {
        return text3;
    }

    public byte[] getImage1() {
        return image1;
    }

    public byte[] getImage2() {
        return image2;
    }

    public byte[] getImage3() {
        return image3;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }
}
