package se.JonathanAnton.bykanalen.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.JonathanAnton.bykanalen.service.EventRegistrationService;

/** REST-controller för att hantera HTTP-förfrågningar kopplade till anmälan (Registrations)
 av användare till evenemang (Events) i en grupp.
 Alla anrop till denna controller startar med bas-URL:en /api/groups/{groupId}/events/{eventId}/registration */
@RestController
@RequestMapping("/api/groups/{groupId}/events/{eventId}/registration")
public class EventRegistrationController {

    private final EventRegistrationService eventRegistrationService;

    public EventRegistrationController(EventRegistrationService eventRegistrationService) {
        this.eventRegistrationService = eventRegistrationService;
    }

    // Endpoint för skapande av anmälan för en användare till evenemang
    @PostMapping
    public ResponseEntity<Void> registerForEvent(@PathVariable Long groupId, @PathVariable Long eventId) {
        eventRegistrationService.registerForEvent(groupId, eventId);
        return ResponseEntity.status(201).build();
    }

    // Endpoint för borttagning av anmälan för en användare till evenemang
    @DeleteMapping
    public ResponseEntity<Void> unregisterFromEvent(@PathVariable Long groupId, @PathVariable Long eventId) {
        eventRegistrationService.unregisterFromEvent(groupId, eventId);
        return ResponseEntity.noContent().build();
    }

    // Endpoint för att kontrollera om en användare är anmäld till ett evenemang
    @GetMapping
    public ResponseEntity<Boolean> getRegistrationStatus(@PathVariable Long groupId, @PathVariable Long eventId) {
        return ResponseEntity.ok(eventRegistrationService.isRegistered(groupId, eventId));
    }
}
