package se.JonathanAnton.bykanalen.mapper;

import org.springframework.stereotype.Component;
import se.JonathanAnton.bykanalen.dto.CreateEventDTO;
import se.JonathanAnton.bykanalen.dto.EventDetailDTO;
import se.JonathanAnton.bykanalen.dto.EventSummaryDTO;
import se.JonathanAnton.bykanalen.model.Event;
import se.JonathanAnton.bykanalen.model.Group;

@Component
public class EventMapper {

    public EventDetailDTO toEventDetailDTO(Event event, long registrationCount) {
        return new EventDetailDTO(
                event.getTitle(),
                event.getImage(),
                event.getDescription(),
                event.getPublishDate(),
                event.getStartDate(),
                event.getEndDate(),
                event.getCloseRegistrationDate(),
                event.getGroup() != null ? event.getGroup().getId() : null,
                registrationCount
        );
    }

    public EventSummaryDTO toEventSummaryDTO(Event event){
        return new EventSummaryDTO(
                event.getId(),
                event.getTitle(),
                event.getImage(),
                event.getStartDate(),
                event.getEndDate()
        );
    }

    public Event toEntity(CreateEventDTO dto, Group group){
        Event event = new Event();
        event.setTitle(dto.getTitle());
        event.setImage(dto.getImage());
        event.setDescription(dto.getDescription());
        event.setStartDate(dto.getStartDate());
        event.setEndDate(dto.getEndDate());
        event.setCloseRegistrationDate(dto.getCloseRegistrationDate());
        event.setGroup(group);
        return event;
    }
}
