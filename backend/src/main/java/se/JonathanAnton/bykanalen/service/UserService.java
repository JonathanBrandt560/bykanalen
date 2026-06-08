package se.JonathanAnton.bykanalen.service;

import org.springframework.stereotype.Service;
import se.JonathanAnton.bykanalen.dto.RegisterDTO;
import se.JonathanAnton.bykanalen.exception.ResourceNotFoundException;
import se.JonathanAnton.bykanalen.mapper.UserMapper;
import se.JonathanAnton.bykanalen.model.Group;
import se.JonathanAnton.bykanalen.model.MemberlistGroup;
import se.JonathanAnton.bykanalen.model.User;
import se.JonathanAnton.bykanalen.model.UserDetail;
import se.JonathanAnton.bykanalen.repository.GroupRepository;
import se.JonathanAnton.bykanalen.repository.MemberlistGroupRepository;
import se.JonathanAnton.bykanalen.repository.UserDetailRepository;
import se.JonathanAnton.bykanalen.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserDetailRepository userDetailRepository;
    private final MemberlistGroupRepository memberlistGroupRepository;
    private final GroupRepository groupRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserDetailRepository userDetailRepository, MemberlistGroupRepository memberlistGroupRepository, GroupRepository groupRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userDetailRepository = userDetailRepository;
        this.memberlistGroupRepository = memberlistGroupRepository;
        this.groupRepository = groupRepository;
        this.userMapper = userMapper;
    }

    public void register(RegisterDTO dto) {
        User user = userMapper.toUserEntity(dto);
        userRepository.save(user);

        UserDetail userDetail = userMapper.toUserDetailEntity(dto, user);
        userDetailRepository.save(userDetail);

        Group group = groupRepository.findById(dto.getGroupId()).orElseThrow(() -> new ResourceNotFoundException("Grupp med id " + dto.getGroupId() + " hittades inte"));

        MemberlistGroup memberlistGroup = userMapper.toMemberlistGroupEntity(user, group);
        memberlistGroupRepository.save(memberlistGroup);
    }
}
