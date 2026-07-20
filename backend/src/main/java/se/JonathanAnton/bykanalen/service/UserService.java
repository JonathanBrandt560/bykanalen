package se.JonathanAnton.bykanalen.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.JonathanAnton.bykanalen.dto.LoginDTO;
import se.JonathanAnton.bykanalen.dto.RegisterDTO;
import se.JonathanAnton.bykanalen.exception.ResourceNotFoundException;
import se.JonathanAnton.bykanalen.mapper.UserMapper;
import se.JonathanAnton.bykanalen.model.*;
import se.JonathanAnton.bykanalen.repository.GroupInfoRepository;
import se.JonathanAnton.bykanalen.repository.MemberlistGroupRepository;
import se.JonathanAnton.bykanalen.repository.UserDetailRepository;
import se.JonathanAnton.bykanalen.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserDetailRepository userDetailRepository;
    private final MemberlistGroupRepository memberlistGroupRepository;
    private final GroupInfoRepository groupInfoRepository;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final se.JonathanAnton.bykanalen.service.JwtService jwtService;

    public UserService(UserRepository userRepository, UserDetailRepository userDetailRepository, MemberlistGroupRepository memberlistGroupRepository, GroupInfoRepository groupInfoRepository, UserMapper userMapper, AuthenticationManager authenticationManager, UserDetailsService userDetailsService, se.JonathanAnton.bykanalen.service.JwtService jwtService) {
        this.userRepository = userRepository;
        this.userDetailRepository = userDetailRepository;
        this.memberlistGroupRepository = memberlistGroupRepository;
        this.groupInfoRepository = groupInfoRepository;
        this.userMapper = userMapper;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
    }

    @Transactional
    public void register(RegisterDTO dto) {
        User user = userMapper.toUserEntity(dto);
        userRepository.save(user);

        UserDetail userDetail = userMapper.toUserDetailEntity(dto, user);
        userDetailRepository.save(userDetail);

        GroupInfo groupInfo = groupInfoRepository.findById(dto.getGroupId()).orElseThrow(() -> new ResourceNotFoundException("Grupp med id " + dto.getGroupId() + " hittades inte"));

        MemberlistGroup memberlistGroup = userMapper.toMemberlistGroupEntity(user, groupInfo);
        memberlistGroupRepository.save(memberlistGroup);
    }

    public String login(LoginDTO dto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword())
        );
        UserDetails userDetails = userDetailsService.loadUserByUsername(dto.getUsername());
        return jwtService.generateToken(userDetails);
    }
}