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

/**
 * Service lager för hantering av tjänster i Bykanalen.
 * Innehåller affärslogik för att hämta och skapa tjänster kopplade till byar/grupper.
 */
@Service
public class ServiceService {

    // Dependencies för databaseåtkomst
    private final ServiceRepository serviceRepository;
    private final AuthorizationService authorizationService;
    private final ServiceMapper serviceMapper;
    private final GroupInfoRepository groupInfoRepository;

    /**
     * Constructor för Dependency Injection (Spring skickar in repositories automatiskt).
     */
    public ServiceService(ServiceRepository serviceRepository,
                          AuthorizationService authorizationService,
                          ServiceMapper serviceMapper,
                          GroupInfoRepository groupInfoRepository) {
        this.serviceRepository = serviceRepository;
        this.authorizationService = authorizationService;
        this.serviceMapper = serviceMapper;
        this.groupInfoRepository = groupInfoRepository;
    }

    /**
     * Hämtar alla tjänster som tillhör en viss grupp/by och omvandlar dem till DTO:er.
     *
     * @param groupId ID på den grupp/by vars tjänster ska hämtas.
     * @return En lista med ServiceDTO-objekt.
     */
    public List<ServiceSummaryDTO> getServicesByGroup(Long groupId) {
        User user = authorizationService.getCurrentUser();
        authorizationService.verifyGroupMembership(groupId, user.getId());

        List<se.JonathanAnton.bykanalen.model.Service> services = serviceRepository.findByGroupInfoIdWithUser(groupId);
        return services.stream()
                .map(serviceMapper::toServiceSummaryDTO)
                .toList();
    }

    public ServiceDetailDTO getServiceById(Long groupId, Long id) {
        User user = authorizationService.getCurrentUser();
        authorizationService.verifyGroupMembership(groupId, user.getId());

        se.JonathanAnton.bykanalen.model.Service service = serviceRepository.findByGroupInfoIdAndId(groupId, id)
                .orElseThrow(() -> new ResourceNotFoundException("Service med id: " + id + " hittades inte"));

        return serviceMapper.toServiceDetailDTO(service);
    }

    public ServiceDetailDTO createService(CreateServiceDTO dto, Long groupId) {
        User user = authorizationService.getCurrentUser();
        authorizationService.verifyGroupMembership(groupId, user.getId());

        GroupInfo group = groupInfoRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Grupp med id: " + groupId + " hittades inte"));
        se.JonathanAnton.bykanalen.model.Service service = serviceMapper.toEntity(dto, group, user);
        se.JonathanAnton.bykanalen.model.Service saved = serviceRepository.save(service);
        return serviceMapper.toServiceDetailDTO(saved);
    }

}
