package se.JonathanAnton.bykanalen.model;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/** Entitetsklass för användare (users) */
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    private Byte age;

    @Column(name= "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    // En användare kan ha en användardetaljer. En användardetaljer kan tillhöra en användare
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private UserDetail userDetail;

    // En användare kan ha många allmänna inlägg. Ett allmänt inlägg kan tillhöra en användare
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<GeneralPost> generalPosts = new ArrayList<>();

    // En användare kan ha många annonser. En annons kan tillhöra en användare
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Listing> listings = new ArrayList<>();

    // En användare kan ha många evenemangsregistreringar. En evenemangsregistrering kan tillhöra en användare
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EventRegistration> eventRegistrations = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MemberlistGroup> memberlistGroups = new ArrayList<>();

    // En användare kan ha många tjänster. En tjänst kan tillhöra en användare
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Service> services = new ArrayList<>();

    public User() {}

    public User(String username, String password, String email, Byte age, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.age = age;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public UserDetail getUserDetail() {
        return userDetail;
    }

    public void setUserDetail(UserDetail userDetail) {
        this.userDetail = userDetail;
    }

    public List<GeneralPost> getGeneralPosts() {
        return generalPosts;
    }

    public void setGeneralPosts(List<GeneralPost> generalPosts) {
        this.generalPosts = generalPosts;
    }

    public List<Listing> getListings() {
        return listings;
    }

    public void setListings(List<Listing> listings) {
        this.listings = listings;
    }

    public List<EventRegistration> getEventRegistrations() {
        return eventRegistrations;
    }

    public void setEventRegistrations(List<EventRegistration> eventRegistrations) {
        this.eventRegistrations = eventRegistrations;
    }

    public List<MemberlistGroup> getMemberlistGroups() {
        return memberlistGroups;
    }

    public void setMemberlistGroups(List<MemberlistGroup> memberlistGroups) {
        this.memberlistGroups = memberlistGroups;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }
}
