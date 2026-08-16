package se.JonathanAnton.bykanalen.service;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import se.JonathanAnton.bykanalen.exception.ResourceNotFoundException;
import se.JonathanAnton.bykanalen.model.User;
import se.JonathanAnton.bykanalen.repository.MemberlistGroupRepository;
import se.JonathanAnton.bykanalen.repository.UserRepository;

/** Service-klass som verifierar användares behörighet  */
@Service
public class AuthorizationService {

    private final UserRepository userRepository;
    private final MemberlistGroupRepository memberlistGroupRepository;

    public AuthorizationService(UserRepository userRepository, MemberlistGroupRepository memberlistGroupRepository) {
        this.userRepository = userRepository;
        this.memberlistGroupRepository = memberlistGroupRepository;
    }

    // Funktionen verifierar en användares grupptillhörighet
    public void verifyGroupMembership(Long groupId) {
        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        // Verifierar att användares användarnamn existerar
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Användare hittades inte"));

        // Verifierar att användare tillhör grupp
        boolean isMember = memberlistGroupRepository
                .existsByUserIdAndGroupInfoId(user.getId(), groupId);

        if(!isMember) {
            throw new ResourceNotFoundException("Du har inte tillgång till denna grupp");
        }
    }
}
