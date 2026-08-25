package se.JonathanAnton.bykanalen.service;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import se.JonathanAnton.bykanalen.enums.UserType;
import se.JonathanAnton.bykanalen.exception.ResourceNotFoundException;
import se.JonathanAnton.bykanalen.model.User;
import se.JonathanAnton.bykanalen.repository.MemberlistGroupRepository;
import se.JonathanAnton.bykanalen.repository.UserDetailRepository;
import se.JonathanAnton.bykanalen.repository.UserRepository;

import org.springframework.security.access.AccessDeniedException;

/** Service-klass som verifierar användares behörighet  */
@Service
public class AuthorizationService {
    private final UserRepository userRepository;
    private final MemberlistGroupRepository memberlistGroupRepository;
    private final UserDetailRepository userDetailRepository;

    public AuthorizationService(UserRepository userRepository, MemberlistGroupRepository memberlistGroupRepository, UserDetailRepository userDetailRepository) {
        this.userRepository = userRepository;
        this.memberlistGroupRepository = memberlistGroupRepository;
        this.userDetailRepository = userDetailRepository;
    }

    // FUnktionen hämtar inloggad användare och returnerar som ett user-objekt
    public User getCurrentUser() {
        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Användare hittades inte"));
    }

    // Funktionen verifierar en användares grupptillhörighet
    public void verifyGroupMembership(Long groupId, Long userId) {
        boolean isMember = memberlistGroupRepository
                .existsByUserIdAndGroupInfoId(userId, groupId);

        if (!isMember) {
            throw new ResourceNotFoundException("Du har inte tillgång till denna grupp");
        }
    }

    // Funktionen verifierar om en inloggad användare har admin-behörighet
    public void verifyAdminStatus(long userId) {
        boolean isAdmin = userDetailRepository.existsByUserIdAndType(userId, UserType.admin);

        if (!isAdmin) {
            throw new AccessDeniedException("Kräver admin-behörighet");
        }
    }
}
