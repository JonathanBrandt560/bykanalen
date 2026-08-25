package se.JonathanAnton.bykanalen.dto;

import jakarta.validation.constraints.NotBlank;

/** DTO för skapande av en ny grupp/by */
public class CreateGroupDTO {

    @NotBlank(message = "Gruppnamn får inte vara tomt")
    private String groupName;

    private String text1;

    private String text2;

    private String text3;

    private byte[] image1;

    private byte[] image2;

    private byte[] image3;

    public CreateGroupDTO() {}

    public CreateGroupDTO(String groupName, String text1, String text2, String text3, byte[] image1, byte[] image2, byte[] image3) {
        this.groupName = groupName;
        this.text1 = text1;
        this.text2 = text2;
        this.text3 = text3;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
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
}
