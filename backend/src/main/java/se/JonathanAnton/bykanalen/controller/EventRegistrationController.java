package se.JonathanAnton.bykanalen.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.JonathanAnton.bykanalen.service.EventRegistrationService;

/**
 * REST-controller för att hantera HTTP-förfrågningar kopplade till användarregistrering till
 * evenemang (Events) i en grupp.
 * Alla anrop till denna controller startar med bas-URL:en /groups/{groupId}/events/{eventId}/registration
 */
@RestController
@RequestMapping("/groups/{groupId}/events/{eventId}/registration")
public class EventRegistrationController {

    private final EventRegistrationService eventRegistrationService;

    public EventRegistrationController(EventRegistrationService eventRegistrationService) {
        this.eventRegistrationService = eventRegistrationService;
    }

    // Endpoint för registrering till evenemang
    @PostMapping
    public ResponseEntity<Void> registerForEvent(@PathVariable Long groupId, @PathVariable Long eventId) {
        eventRegistrationService.registerForEvent(groupId, eventId);
        return ResponseEntity.status(201).build();
    }

    // Endpoint för avregistrering till evenemang
    @DeleteMapping
    public ResponseEntity<Void> unregisterFromEvent(@PathVariable Long groupId, @PathVariable Long eventId) {
        eventRegistrationService.unregisterFromEvent(groupId, eventId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Boolean> getRegistrationStatus(@PathVariable Long groupId, @PathVariable Long eventId) {
        return ResponseEntity.ok(eventRegistrationService.isRegistered(groupId, eventId));
    }
}
