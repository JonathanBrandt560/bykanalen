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

@RestController
@RequestMapping("/groups/{groupId}/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/upcoming")
    public ResponseEntity<List<EventSummaryDTO>> getUpcomingEvents(@PathVariable Long groupId) {
        return ResponseEntity.ok(eventService.getUpcomingEvents(groupId));
    }

    @GetMapping("/after")
    public ResponseEntity<List<EventSummaryDTO>> getEventsAfterDate(@PathVariable Long groupId, @RequestParam LocalDate date) {
        return ResponseEntity.ok(eventService.getEventsAfterDate(groupId, date));
    }

    @GetMapping("/before")
    public ResponseEntity<List<EventSummaryDTO>> getEventsBeforeDate(@PathVariable Long groupId, @RequestParam LocalDate date) {
        return ResponseEntity.ok(eventService.getEventsBeforeDate(groupId, date));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventDetailDTO> getEventById(@PathVariable Long groupId, @PathVariable Long id) {
        return ResponseEntity.ok(eventService.getEventById(groupId, id));
    }

    @PostMapping
    public ResponseEntity<EventDetailDTO> createEvent(@PathVariable Long groupId, @Valid @RequestBody CreateEventDTO dto) {
        return ResponseEntity.status(201).body(eventService.createEvent(dto, groupId));
    }
}