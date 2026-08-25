package se.JonathanAnton.bykanalen.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.JonathanAnton.bykanalen.dto.CreateEventDTO;
import se.JonathanAnton.bykanalen.dto.EventDetailDTO;
import se.JonathanAnton.bykanalen.dto.EventSummaryDTO;
import se.JonathanAnton.bykanalen.service.EventService;
import java.time.LocalDate;
import java.util.List;

/**
 * REST-controller för att hantera HTTP-förfrågningar kopplade till evenemang (Events) i en grupp.
 * Alla anrop till denna controller startar med bas-URL:en /groups/{groupId}/events
 */
@RestController
@RequestMapping("/api/groups/{groupId}/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    // Endpoint som hämtar framtida evenemang
    @GetMapping("/upcoming")
    public ResponseEntity<List<EventSummaryDTO>> getUpcomingEvents(@PathVariable Long groupId) {
        return ResponseEntity.ok(eventService.getUpcomingEvents(groupId));
    }

    /* Endpoint som hämtar evenemang som äger rum EFTER specificerat datum
    Datum skickas med som en Request parameter (?date=YYYY-MM-DD) */
    @GetMapping("/after")
    public ResponseEntity<List<EventSummaryDTO>> getEventsAfterDate(@PathVariable Long groupId, @RequestParam LocalDate date) {
        return ResponseEntity.ok(eventService.getEventsAfterDate(groupId, date));
    }

    /* Endpoint som hämtar evenamng som äger rum INNAN specificerat datum
    Datum skickas med som en Request parameter (?date=YYYY-MM-DD */
    @GetMapping("/before")
    public ResponseEntity<List<EventSummaryDTO>> getEventsBeforeDate(@PathVariable Long groupId, @RequestParam LocalDate date) {
        return ResponseEntity.ok(eventService.getEventsBeforeDate(groupId, date));
    }

    /* Endpoint som hämtar det evenemang vars id specificerats
    Returnerar en detaljvy av evenemanget */
    @GetMapping("/{id}")
    public ResponseEntity<EventDetailDTO> getEventById(@PathVariable Long groupId, @PathVariable Long id) {
        return ResponseEntity.ok(eventService.getEventById(groupId, id));
    }

    /* Endpoint som skickar ett nytt evenemang
    Validering på variablerna som skickas in (@Valid) */
    @PostMapping
    public ResponseEntity<EventDetailDTO> createEvent(@PathVariable Long groupId, @Valid @RequestBody CreateEventDTO dto) {
        return ResponseEntity.status(201).body(eventService.createEvent(dto, groupId));
    }

    // Endpoint som tar bort det evenemang vars id specificerats
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long groupId, @PathVariable Long id) {
        eventService.deleteEvent(groupId, id);
        return ResponseEntity.noContent().build();
    }
}