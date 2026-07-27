package se.JonathanAnton.bykanalen.service;

import org.springframework.stereotype.Service;
import se.JonathanAnton.bykanalen.dto.CreateEventDTO;
import se.JonathanAnton.bykanalen.dto.EventDetailDTO;
import se.JonathanAnton.bykanalen.dto.EventSummaryDTO;
import se.JonathanAnton.bykanalen.exception.ResourceNotFoundException;
import se.JonathanAnton.bykanalen.mapper.EventMapper;
import se.JonathanAnton.bykanalen.model.Event;
import se.JonathanAnton.bykanalen.model.Group;
import se.JonathanAnton.bykanalen.repository.EventRegistrationRepository;
import se.JonathanAnton.bykanalen.repository.EventRepository;
import se.JonathanAnton.bykanalen.repository.GroupRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final EventRegistrationRepository eventRegistrationRepository;
    private final EventMapper eventMapper;
    private final GroupRepository groupRepository;
    private final AuthorizationService authorizationService;

    public EventService(EventRepository eventRepository,
                        EventRegistrationRepository eventRegistrationRepository,
                        EventMapper eventMapper,
                        GroupRepository groupRepository,
                        AuthorizationService authorizationService) {
        this.eventRepository = eventRepository;
        this.eventRegistrationRepository = eventRegistrationRepository;
        this.eventMapper = eventMapper;
        this.groupRepository = groupRepository;
        this.authorizationService = authorizationService;
    }

    public List<EventSummaryDTO> getUpcomingEvents(Long groupId) {
        authorizationService.verifyGroupMembership(groupId);
        LocalDateTime startOfDay = LocalDateTime.now().toLocalDate().atStartOfDay();
        List<Event> events = eventRepository.findByGroupIdAndStartDateAfterOrderByStartDateDesc(groupId, startOfDay);
        return events.stream()
                .map(eventMapper::toEventSummaryDTO)
                .toList();
    }

    public List<EventSummaryDTO> getEventsAfterDate(Long groupId, LocalDate date) {
        authorizationService.verifyGroupMembership(groupId);
        LocalDateTime startOfDay = date.atStartOfDay();
        List<Event> events = eventRepository.findByGroupIdAndStartDateAfterOrderByStartDateDesc(groupId, startOfDay);
        return events.stream()
                .map(eventMapper::toEventSummaryDTO)
                .toList();
    }

    public List<EventSummaryDTO> getEventsBeforeDate(Long groupId, LocalDate date) {
        authorizationService.verifyGroupMembership(groupId);
        LocalDateTime startOfDay = date.atStartOfDay();
        List<Event> events = eventRepository.findByGroupIdAndStartDateBeforeOrderByStartDateDesc(groupId, startOfDay);
        return events.stream()
                .map(eventMapper::toEventSummaryDTO)
                .toList();
    }

    public EventDetailDTO getEventById(Long groupId, Long id) {
        authorizationService.verifyGroupMembership(groupId);
        Event event = eventRepository.findByGroupIdAndId(groupId, id)
                .orElseThrow(() -> new ResourceNotFoundException("Event med id " + id + " hittades inte"));
        long count = eventRegistrationRepository.countByEventId(id);
        return eventMapper.toEventDetailDTO(event, count);
    }

    public EventDetailDTO createEvent(CreateEventDTO dto, Long groupId) {
        authorizationService.verifyGroupMembership(groupId);
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Grupp med id " + groupId + " hittades inte"));
        Event event = eventMapper.toEntity(dto, group);
        Event saved = eventRepository.save(event);
        return eventMapper.toEventDetailDTO(saved, 0);
    }
}