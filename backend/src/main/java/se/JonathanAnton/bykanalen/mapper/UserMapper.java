package se.JonathanAnton.bykanalen.mapper;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import se.JonathanAnton.bykanalen.dto.RegisterDTO;
import se.JonathanAnton.bykanalen.enums.UserType;
import se.JonathanAnton.bykanalen.model.*;

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
                dto.getEmail(),
                dto.getAge(),
                dto.getFirstName(),
                dto.getLastName()
        );
    }

    public UserDetail toUserDetailEntity(RegisterDTO dto, User user) {
        return new UserDetail(
                user,
                UserType.standard,
                false
        );
    }

    public MemberlistGroup toMemberlistGroupEntity(User user, GroupInfo groupInfo) {
        return new MemberlistGroup(user, groupInfo);
    }
}
