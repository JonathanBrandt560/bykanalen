package se.JonathanAnton.bykanalen.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import se.JonathanAnton.bykanalen.exception.ResourceNotFoundException;
import se.JonathanAnton.bykanalen.model.User;
import se.JonathanAnton.bykanalen.repository.MemberlistGroupRepository;
import se.JonathanAnton.bykanalen.repository.UserRepository;

@Service
public class AuthorizationService {

    private final UserRepository userRepository;
    private final MemberlistGroupRepository memberlistGroupRepository;

    public AuthorizationService(UserRepository userRepository, MemberlistGroupRepository memberlistGroupRepository) {
        this.userRepository = userRepository;
        this.memberlistGroupRepository = memberlistGroupRepository;
    }

    public void verifyGroupMembership(Long groupId) {
        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Användare hittades inte"));

        boolean isMember = memberlistGroupRepository
                .existsByUserIdAndGroupId(user.getId(), groupId);

        if(!isMember) {
            throw new ResourceNotFoundException("Du har inte tillgång till denna grupp");
        }
    }
}
