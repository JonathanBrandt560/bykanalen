package se.JonathanAnton.bykanalen.dto;

import jakarta.validation.constraints.NotBlank;

public class CreateUserDTO {

    @NotBlank(message = "Du måsste välja ett användarnamn")
    private String username;

    @NotBlank(message = "Du måste välja ett lösenord")
    private String password;

    @NotBlank(message = "Du måste skriva en email-adress")
    private String email;

    private Byte age;

    @NotBlank(message = "Förnamn får inte vara tomt")
    private String firstName;

    @NotBlank(message = "Efternamn får inte vara tomt")
    private String lastName;

    public CreateUserDTO() {}

    public CreateUserDTO(String username, String password, String email, Byte age, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.age = age;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Byte getAge() {
        return age;
    }

    public void setAge(Byte age) {
        this.age = age;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
