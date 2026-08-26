package se.JonathanAnton.bykanalen.mapper;

import org.springframework.stereotype.Component;
import se.JonathanAnton.bykanalen.dto.CreateServiceDTO;
import se.JonathanAnton.bykanalen.dto.ServiceDetailDTO;
import se.JonathanAnton.bykanalen.dto.ServiceSummaryDTO;
import se.JonathanAnton.bykanalen.model.GroupInfo;
import se.JonathanAnton.bykanalen.model.Service;
import se.JonathanAnton.bykanalen.model.User;

@Component
public class ServiceMapper {

    /**
     * Hjälpmetod för att omvandla en Service-entitet till en ServiceDTO.
     *
     * @param service Databasentiteten som ska omvandlas.
     * @return En ServiceDTO redo att skickas som svar till klienten.
     */
    public ServiceDetailDTO toServiceDetailDTO(Service service) {
        return new ServiceDetailDTO(
                service.getTitle(),
                service.getDescription(),
                service.getImage(),
                service.getPublishDate(),
                service.getUser() != null ? service.getUser().getUsername() :null
        );
    }

    public ServiceSummaryDTO toServiceSummaryDTO(Service service) {
        return new ServiceSummaryDTO(
                service.getId(),
                service.getTitle(),
                service.getImage(),
                service.getPublishDate(),
                service.getUser() != null ? service.getUser().getUsername() :null
        );
    }

    public Service toEntity(CreateServiceDTO dto, GroupInfo groupinfo, User user) {
        Service service = new Service();
        service.setTitle(dto.getTitle());
        service.setDescription(dto.getDescription());
        service.setImage(dto.getImage());
        service.setGroup(groupinfo);
        service.setUser(user);
        return service;
    }
}
