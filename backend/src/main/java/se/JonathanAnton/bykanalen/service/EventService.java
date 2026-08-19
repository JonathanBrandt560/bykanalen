package se.JonathanAnton.bykanalen.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.JonathanAnton.bykanalen.dto.CreateEventDTO;
import se.JonathanAnton.bykanalen.dto.EventDetailDTO;
import se.JonathanAnton.bykanalen.dto.EventSummaryDTO;
import se.JonathanAnton.bykanalen.exception.ResourceNotFoundException;
import se.JonathanAnton.bykanalen.mapper.EventMapper;
import se.JonathanAnton.bykanalen.model.Event;
import se.JonathanAnton.bykanalen.model.GroupInfo;
import se.JonathanAnton.bykanalen.model.User;
import se.JonathanAnton.bykanalen.repository.EventRegistrationRepository;
import se.JonathanAnton.bykanalen.repository.EventRepository;
import se.JonathanAnton.bykanalen.repository.GroupInfoRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventService {
    private final EventRepository eventRepository;
    private final EventRegistrationRepository eventRegistrationRepository;
    private final EventMapper eventMapper;
    private final GroupInfoRepository groupInfoRepository;
    private final AuthorizationService authorizationService;

    public EventService(EventRepository eventRepository,
                        EventRegistrationRepository eventRegistrationRepository,
                        EventMapper eventMapper,
                        GroupInfoRepository groupInfoRepository,
                        AuthorizationService authorizationService) {
        this.eventRepository = eventRepository;
        this.eventRegistrationRepository = eventRegistrationRepository;
        this.eventMapper = eventMapper;
        this.groupInfoRepository = groupInfoRepository;
        this.authorizationService = authorizationService;
    }

    // Hämtar evenemang som inträffar i framtiden
    public List<EventSummaryDTO> getUpcomingEvents(Long groupId) {
        User user = authorizationService.getCurrentUser();
        authorizationService.verifyGroupMembership(groupId, user.getId());
        // Initierar variabel som räknar ut dagens datum klockan 00.00 i lokal tidszon
        LocalDateTime startOfDay = LocalDateTime.now().toLocalDate().atStartOfDay();
        List<Event> events = eventRepository.findByGroupInfoIdAndStartDateAfterOrderByStartDateDesc(groupId, startOfDay);
        return events.stream()
                .map(eventMapper::toEventSummaryDTO)
                .toList();
    }

    // Hämtar evenemang som äger rum efter ett givet datum
    public List<EventSummaryDTO> getEventsAfterDate(Long groupId, LocalDate date) {
        User user = authorizationService.getCurrentUser();
        authorizationService.verifyGroupMembership(groupId, user.getId());
        // Initierar variabel som tar emot ett datum från parameter och omvandlar till klockan 00.00 i lokal tidszon
        LocalDateTime startOfDay = date.atStartOfDay();
        List<Event> events = eventRepository.findByGroupInfoIdAndStartDateAfterOrderByStartDateDesc(groupId, startOfDay);
        return events.stream()
                .map(eventMapper::toEventSummaryDTO)
                .toList();
    }

    // Hämtar evenemang som äger rum innan ett givet datum
    public List<EventSummaryDTO> getEventsBeforeDate(Long groupId, LocalDate date) {
        User user = authorizationService.getCurrentUser();
        authorizationService.verifyGroupMembership(groupId, user.getId());
        LocalDateTime startOfDay = date.atStartOfDay();
        List<Event> events = eventRepository.findByGroupInfoIdAndStartDateBeforeOrderByStartDateDesc(groupId, startOfDay);
        return events.stream()
                .map(eventMapper::toEventSummaryDTO)
                .toList();
    }

    // Hämtar ett event givet dess evenemangs-id. Returnerar ett EventDetailDTO
    public EventDetailDTO getEventById(Long groupId, Long id) {
        User user = authorizationService.getCurrentUser();
        authorizationService.verifyGroupMembership(groupId, user.getId());
        Event event = eventRepository.findByGroupInfoIdAndId(groupId, id)
                .orElseThrow(() -> new ResourceNotFoundException("Event med id " + id + " hittades inte"));
        // Initierar variabel som hämtar antalet evenemangsregistreringar för specifikt event-id
        long count = eventRegistrationRepository.countByEventId(id);
        return eventMapper.toEventDetailDTO(event, count);
    }

    /* Skapar ett nytt evenemang. Kräver att inloggad användare är admin och tillhör gruppen som eventet postas i.
    Tar emot en dto och mappas om till en event-entitet.
    Returnerar slutligen en EventDetailDTO */
    @Transactional
    public EventDetailDTO createEvent(CreateEventDTO dto, Long groupId) {
        User user = authorizationService.getCurrentUser();
        authorizationService.verifyGroupMembership(groupId, user.getId());
        authorizationService.verifyAdminStatus(user.getId());

        GroupInfo group = groupInfoRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Grupp med id " + groupId + " hittades inte"));
        Event event = eventMapper.toEntity(dto, group);
        Event saved = eventRepository.save(event);
        return eventMapper.toEventDetailDTO(saved, 0);
    }

     // Raderar ett evenemang. Kräver att inloggad användare är admin och tillhör gruppen som eventet tillhör.
    @Transactional
    public void deleteEvent(Long groupId, Long eventId)  {
        User user = authorizationService.getCurrentUser();
        authorizationService.verifyGroupMembership(groupId, user.getId());
        authorizationService.verifyAdminStatus(user.getId());

        Event event = eventRepository.findByGroupInfoIdAndId(groupId, eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event med id " + eventId + " hittades inte"));

        eventRegistrationRepository.deleteAllByEventId(eventId);
        eventRepository.delete(event);
    }
}