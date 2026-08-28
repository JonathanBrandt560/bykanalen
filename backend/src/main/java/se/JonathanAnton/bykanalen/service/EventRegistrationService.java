package se.JonathanAnton.bykanalen.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.JonathanAnton.bykanalen.exception.ResourceNotFoundException;
import se.JonathanAnton.bykanalen.model.Event;
import se.JonathanAnton.bykanalen.model.EventRegistration;
import se.JonathanAnton.bykanalen.model.User;
import se.JonathanAnton.bykanalen.repository.EventRegistrationRepository;
import se.JonathanAnton.bykanalen.repository.EventRepository;

/** Service-lager för hantering av användares anmälningar (Registration) till evenemang i Bykanalen. */
@Service
public class EventRegistrationService {
    private final EventRegistrationRepository eventRegistrationRepository;
    private final EventRepository eventRepository;
    private final AuthorizationService authorizationService;

    public EventRegistrationService(EventRegistrationRepository eventRegistrationRepository,
                                    EventRepository eventRepository,
                                    AuthorizationService authorizationService) {
        this.eventRegistrationRepository = eventRegistrationRepository;
        this.eventRepository = eventRepository;
        this.authorizationService = authorizationService;
    }

    /* Registrerar en användare till det evenemang id vars id specificerats.
    Utförs som en transaktion för att se till så att all eller ingen tillhörande information sparas */
    @Transactional
    public void registerForEvent(Long groupId, Long eventId) {
        // Säkerställer att endast inloggade medlemmar av gruppen kan registrera sig till events i den
        User user = authorizationService.getCurrentUser();
        authorizationService.verifyGroupMembership(groupId, user.getId());

        Event event = eventRepository.findByGroupInfoIdAndId(groupId, eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event med id: " + eventId + " hittades inte"));

        // Förhindrar dubbel-anmälan
        if (eventRegistrationRepository.existsByUserIdAndEventId(user.getId(), eventId)) {
            throw new IllegalStateException("Du är redan registrerad på detta event");
        }

        eventRegistrationRepository.save(new EventRegistration(user, event));
    }

    /* Avregistrerar en användare från ett evenemang den tidigare anmält sig till.
    Utförs som en transaktion för att se till att all eller ingen tillhörande information sparas. */
    @Transactional
    public void unregisterFromEvent(Long groupId, Long eventId) {
        /* Säkerställer gruppmedlemskap, samt att endast den som har den specifika
        evenemangsregistreringen kan ta bort den (findByUserIdAndEventId hittar bara
        registreringen om den tillhör den inloggade användaren). */
        User user = authorizationService.getCurrentUser();
        authorizationService.verifyGroupMembership(groupId,  user.getId());

        EventRegistration registration = eventRegistrationRepository
                .findByUserIdAndEventId(user.getId(), eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Du är inte registrerad på detta event"));

        eventRegistrationRepository.delete(registration);
    }

    // Undersöker om en användare är registrerad till ett evenemangg vars id specificerats
    public boolean isRegistered(Long groupId, Long eventId) {
        User user = authorizationService.getCurrentUser();
        authorizationService.verifyGroupMembership(groupId,  user.getId());

        return eventRegistrationRepository.existsByUserIdAndEventId(user.getId(), eventId);
    }
}
