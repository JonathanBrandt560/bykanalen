package se.JonathanAnton.bykanalen.service;

import org.springframework.stereotype.Service;
import se.JonathanAnton.bykanalen.dto.CreateServiceDTO;
import se.JonathanAnton.bykanalen.dto.ServiceDTO;
import se.JonathanAnton.bykanalen.exception.ResourceNotFoundException;
import se.JonathanAnton.bykanalen.model.Group;
import se.JonathanAnton.bykanalen.model.User;
import se.JonathanAnton.bykanalen.repository.GroupRepository;
import se.JonathanAnton.bykanalen.repository.ServiceRepository;
import se.JonathanAnton.bykanalen.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceService {

    private final ServiceRepository serviceRepository;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    public ServiceService(ServiceRepository serviceRepository,
                          GroupRepository groupRepository,
                          UserRepository userRepository) {
        this.serviceRepository = serviceRepository;
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
    }

    public List<ServiceDTO> getServicesByGroup(Long groupId) {
        return serviceRepository.findByGroupId(groupId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ServiceDTO createService(Long groupId, Long userId, CreateServiceDTO dto) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Grupp med id " + groupId + " hittades inte"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Användare med id " + userId + " hittades inte"));

        se.JonathanAnton.bykanalen.model.Service service = new se.JonathanAnton.bykanalen.model.Service();
        service.setTitle(dto.getTitle());
        service.setDescription(dto.getDescription());
        service.setImage(dto.getImage());
        service.setGroup(group);
        service.setUser(user);

        se.JonathanAnton.bykanalen.model.Service savedService = serviceRepository.save(service);

        return convertToDTO(savedService);
    }

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