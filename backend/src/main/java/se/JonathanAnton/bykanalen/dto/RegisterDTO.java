package se.JonathanAnton.bykanalen.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class RegisterDTO {

    @NotBlank(message = "Användarnamn får inte vara tomt")
    private String username;

    @NotBlank(message = "Lösenord får inte vara tomt")
    private String password;

    @NotBlank(message = "Email får inte vara tomt")
    private String email;

    @NotNull(message = "Ålder får inte vara tomt")
    @Min(value = 18, message = "Du måste ha fyllt 18 år")
    private Byte age;

    @NotBlank(message = "Förnamn får inte vara tomt")
    private String firstName;

    @NotBlank(message = "Efternamn får inte vara tomt")
    private String lastName;

    @NotNull(message = "Gruppid får inte vara tomt")
    private Long groupId;

    public RegisterDTO() {}

    public RegisterDTO(String username, String password, String email, Byte age, String firstName, String lastName, Long groupId) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.age = age;
        this.firstName = firstName;
        this.lastName = lastName;
        this.groupId = groupId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public Byte getAge() {
        return age;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Long getGroupId() {return groupId;}

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAge(Byte age) {
        this.age = age;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }
}
