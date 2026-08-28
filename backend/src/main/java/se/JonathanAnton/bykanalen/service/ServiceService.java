package se.JonathanAnton.bykanalen.service;

import org.springframework.stereotype.Service;
import se.JonathanAnton.bykanalen.dto.CreateServiceDTO;
import se.JonathanAnton.bykanalen.dto.ServiceDetailDTO;
import se.JonathanAnton.bykanalen.dto.ServiceSummaryDTO;
import se.JonathanAnton.bykanalen.exception.ResourceNotFoundException;
import se.JonathanAnton.bykanalen.mapper.ServiceMapper;
import se.JonathanAnton.bykanalen.model.GroupInfo;
import se.JonathanAnton.bykanalen.model.User;

import se.JonathanAnton.bykanalen.repository.GroupInfoRepository;
import se.JonathanAnton.bykanalen.repository.ServiceRepository;



import java.util.List;

/** Service-lager för hantering av tjänster (services) i Bykanalen.
 Innehåller affärslogik för att hämta och skapa tjänster kopplade till byar/grupper.
 Obs: modellklassen Service refereras med fullt kvalificerat namn genomgående,
 eftersom "Service" annars krockar med @Service-annotationen som redan importerats. */
@Service
public class ServiceService {

    private final ServiceRepository serviceRepository;
    private final AuthorizationService authorizationService;
    private final ServiceMapper serviceMapper;
    private final GroupInfoRepository groupInfoRepository;

    public ServiceService(ServiceRepository serviceRepository,
                          AuthorizationService authorizationService,
                          ServiceMapper serviceMapper,
                          GroupInfoRepository groupInfoRepository) {
        this.serviceRepository = serviceRepository;
        this.authorizationService = authorizationService;
        this.serviceMapper = serviceMapper;
        this.groupInfoRepository = groupInfoRepository;
    }

    /* Hämtar alla tjänster som tillhör en viss grupp/by och omvandlar dem till DTO:er.
    Tar emot id för den grupp/by vars tjänster ska hämtas och
    returnerar en lista med ServiceSummaryDTO-objekt. */
    public List<ServiceSummaryDTO> getServicesByGroup(Long groupId) {
        // Säkerställer att endast inloggade medlemmar av gruppen kan hämta tjänster i den
        User user = authorizationService.getCurrentUser();
        authorizationService.verifyGroupMembership(groupId, user.getId());

        List<se.JonathanAnton.bykanalen.model.Service> services = serviceRepository.findByGroupInfoIdWithUser(groupId);
        return services.stream()
                .map(serviceMapper::toServiceSummaryDTO)
                .toList();
    }

    /* Hämtar en tjänst och omvandlar den till en ServiceDetailDTO.
    Tar emot id för den grupp/by samt tjänstens id vars tjänst ska hämtas, och
    returnerar en ServiceDetailDTO. */
    public ServiceDetailDTO getServiceById(Long groupId, Long id) {
        // Säkerställer att endast inloggade medlemmar av gruppen kan hämta tjänster i den
        User user = authorizationService.getCurrentUser();
        authorizationService.verifyGroupMembership(groupId, user.getId());

        se.JonathanAnton.bykanalen.model.Service service = serviceRepository.findByGroupInfoIdAndId(groupId, id)
                .orElseThrow(() -> new ResourceNotFoundException("Service med id: " + id + " hittades inte"));

        return serviceMapper.toServiceDetailDTO(service);
    }

    /* Skapar en ny tjänst i angiven grupp/by, kopplad till den inloggade användaren.
    Verifierar gruppmedlemskap, mappar in-datan till en Service-entitet, sparar den,
    och returnerar den skapade tjänsten som en ServiceDetailDTO. */
    public ServiceDetailDTO createService(CreateServiceDTO dto, Long groupId) {
        // Säkerställer att endast inloggade medlemmar av gruppen kan skapa tjänster i den
        User user = authorizationService.getCurrentUser();
        authorizationService.verifyGroupMembership(groupId, user.getId());

        GroupInfo group = groupInfoRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Grupp med id: " + groupId + " hittades inte"));

        se.JonathanAnton.bykanalen.model.Service service = serviceMapper.toEntity(dto, group, user);
        se.JonathanAnton.bykanalen.model.Service saved = serviceRepository.save(service);
        return serviceMapper.toServiceDetailDTO(saved);
    }

}
