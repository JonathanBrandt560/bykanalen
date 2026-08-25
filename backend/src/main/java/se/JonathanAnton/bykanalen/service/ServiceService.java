package se.JonathanAnton.bykanalen.service;

import org.springframework.stereotype.Service;
import se.JonathanAnton.bykanalen.dto.CreateServiceDTO;
import se.JonathanAnton.bykanalen.dto.ServiceDTO;
import se.JonathanAnton.bykanalen.exception.ResourceNotFoundException;
import se.JonathanAnton.bykanalen.model.GroupInfo;
import se.JonathanAnton.bykanalen.model.User;
import se.JonathanAnton.bykanalen.repository.GroupInfoRepository;
import se.JonathanAnton.bykanalen.repository.ServiceRepository;
import se.JonathanAnton.bykanalen.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service lager för hantering av tjänster i Bykanalen.
 * Innehåller affärslogik för att hämta och skapa tjänster kopplade till byar/grupper.
 */
@Service
public class ServiceService {

    // Dependencies för databaseåtkomst
    private final ServiceRepository serviceRepository;
    private final GroupInfoRepository groupInfoRepository;
    private final UserRepository userRepository;

    /**
     * Constructor för Dependency Injection (Spring skickar in repositories automatiskt).
     */
    public ServiceService(ServiceRepository serviceRepository,
                          GroupInfoRepository groupInfoRepository,
                          UserRepository userRepository) {
        this.serviceRepository = serviceRepository;
        this.groupInfoRepository = groupInfoRepository;
        this.userRepository = userRepository;
    }

    /**
     * Hämtar alla tjänster som tillhör en viss grupp/by och omvandlar dem till DTO:er.
     *
     * @param groupId ID på den grupp/by vars tjänster ska hämtas.
     * @return En lista med ServiceDTO-objekt.
     */
    public List<ServiceDTO> getServicesByGroup(Long groupId) {
        return serviceRepository.findByGroupInfoId(groupId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Skapar och sparar en ny tjänst i databasen.
     *
     * @param groupId ID för gruppen/byn där tjänsten ska skapas.
     * @param userId ID för användaren som skapar tjänsten.
     * @param dto Inkommande data för den nya tjänsten.
     * @return Den skapade tjänsten omvandlad till ServiceDTO.
     */
    public ServiceDTO createService(Long groupId, Long userId, CreateServiceDTO dto) {
        // 1. Verifiera att gruppen finns i databasen, annars kasta ett undantag
        GroupInfo group = groupInfoRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Grupp med id " + groupId + " hittades inte"));

        // 2. Verifiera att användaren finns i databasen, annars kasta ett undantag
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Användare med id " + userId + " hittades inte"));

        // 3. Skapa en ny instans av databasmodellen Service och sätt dess fält från DTO och entiteter
        se.JonathanAnton.bykanalen.model.Service service = new se.JonathanAnton.bykanalen.model.Service();
        service.setTitle(dto.getTitle());
        service.setDescription(dto.getDescription());
        service.setImage(dto.getImage());
        service.setGroup(group);
        service.setUser(user);

        // 4. Spara entiteten i databasen via repositoryt
        se.JonathanAnton.bykanalen.model.Service savedService = serviceRepository.save(service);

        // 5. Omvandla det sparade objektet till en DTO och returnera
        return convertToDTO(savedService);
    }

    /**
     * Hjälpmetod för att omvandla en Service-entitet till en ServiceDTO.
     *
     * @param service Databasentiteten som ska omvandlas.
     * @return En ServiceDTO redo att skickas som svar till klienten.
     */
    private ServiceDTO convertToDTO(se.JonathanAnton.bykanalen.model.Service service) {
        return new ServiceDTO(
                service.getId(),
                service.getTitle(),
                service.getDescription(),
                service.getImage(),
                service.getPublishDate()
        );
    }
}
