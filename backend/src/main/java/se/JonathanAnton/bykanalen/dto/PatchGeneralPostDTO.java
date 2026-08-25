package se.JonathanAnton.bykanalen.dto;

import jakarta.validation.constraints.Size;

public class PatchGeneralPostDTO {

    @Size(min = 1, max = 100, message = "Titel måste var mellan 1 och 100 tecken")
    private String title;

    @Size(max = 500, message = "Beskrivning får vara max 500 tecken")
    private String description;

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
}
