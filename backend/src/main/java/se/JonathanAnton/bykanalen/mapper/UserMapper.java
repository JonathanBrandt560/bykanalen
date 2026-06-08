package se.JonathanAnton.bykanalen.mapper;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import se.JonathanAnton.bykanalen.dto.RegisterDTO;
import se.JonathanAnton.bykanalen.enums.Role;
import se.JonathanAnton.bykanalen.model.Group;
import se.JonathanAnton.bykanalen.model.MemberlistGroup;
import se.JonathanAnton.bykanalen.model.User;
import se.JonathanAnton.bykanalen.model.UserDetail;

@Component
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    public UserMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User toUserEntity(RegisterDTO dto) {
        return new User(
                dto.getUsername(),
                passwordEncoder.encode(dto.getPassword()),
                Role.USER
        );
    }

    public UserDetail toUserDetailEntity(RegisterDTO dto, User user) {
        return new UserDetail(
                user,
                dto.getEmail(),
                dto.getAge(),
                dto.getFirstName(),
                dto.getLastName(),
                false
        );
    }

    public MemberlistGroup toMemberlistGroupEntity(User user, Group group) {
        return new MemberlistGroup(user, group);
    }
}
