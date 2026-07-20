package se.JonathanAnton.bykanalen.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "memberlist_groups")
public class MemberlistGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    private GroupInfo groupInfo;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime joinedDate;

    public MemberlistGroup() {}

    public MemberlistGroup(User user, GroupInfo groupInfo) {
        this.user = user;
        this.groupInfo = groupInfo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public GroupInfo getGroup() {
        return groupInfo;
    }

    public void setGroup(GroupInfo groupInfo) {
        this.groupInfo = groupInfo;
    }

    public LocalDateTime getJoinedDate() {
        return joinedDate;
    }

    public void setJoinedDate(LocalDateTime joinedDate) {
        this.joinedDate = joinedDate;
    }
}
