package se.JonathanAnton.bykanalen.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.JonathanAnton.bykanalen.exception.ResourceNotFoundException;
import se.JonathanAnton.bykanalen.model.Event;
import se.JonathanAnton.bykanalen.model.EventRegistration;
import se.JonathanAnton.bykanalen.model.User;
import se.JonathanAnton.bykanalen.repository.EventRegistrationRepository;
import se.JonathanAnton.bykanalen.repository.EventRepository;
import se.JonathanAnton.bykanalen.repository.UserRepository;

@Service
public class EventRegistrationService {

    private final EventRegistrationRepository eventRegistrationRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final AuthorizationService authorizationService;

    public EventRegistrationService(EventRegistrationRepository eventRegistrationRepository,
                                    EventRepository eventRepository,
                                    UserRepository userRepository,
                                    AuthorizationService authorizationService) {
        this.eventRegistrationRepository = eventRegistrationRepository;
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.authorizationService = authorizationService;
    }

    @Transactional
    public void registerForEvent(Long groupId, Long eventId, String username) {
        authorizationService.verifyGroupMembership(groupId);

        Event event = eventRepository.findByGroupInfoIdAndId(groupId, eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event med id: " + eventId + " hittades inte"));

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Användare hittades inte"));

        if (eventRegistrationRepository.existsByUserIdAndEventId(user.getId(), eventId)) {
            throw new IllegalStateException("Du är redan registrerad på detta event");
        }

        eventRegistrationRepository.save(new EventRegistration(user, event));
    }

    @Transactional
    public void unregisterFromEvent(Long groupId, Long eventId, String username) {
        authorizationService.verifyGroupMembership(groupId);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Användare hittades inte"));

        EventRegistration registration = eventRegistrationRepository
                .findByUserIdAndEventId(user.getId(), eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Du är inte registrerad på detta event"));

        eventRegistrationRepository.delete(registration);
    }

    public boolean isRegistered(Long groupId, Long eventId, String username) {
        authorizationService.verifyGroupMembership(groupId);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Användare hittades inte"));

        return eventRegistrationRepository.existsByUserIdAndEventId(user.getId(), eventId);
    }
}
