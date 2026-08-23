package se.JonathanAnton.bykanalen.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.JonathanAnton.bykanalen.dto.CreateServiceDTO;
import se.JonathanAnton.bykanalen.dto.ServiceDTO;
import se.JonathanAnton.bykanalen.model.User;
import se.JonathanAnton.bykanalen.service.AuthorizationService;
import se.JonathanAnton.bykanalen.service.ServiceService;

import java.util.List;

/**
 * REST-controller för att hantera HTTP-förfrågningar kopplade till tjänster (Services) i en grupp.
 * Alla anrop till denna controller startar med bas-URL:en /api/groups/{groupId}/services
 */
@RestController
@RequestMapping("/api/groups/{groupId}/services")
public class ServiceController {

    // Injektering av servicelagren som innehåller affärslogiken
    @Autowired
    private ServiceService serviceService;

    @Autowired
    private AuthorizationService authorizationService;

    /**
     * HTTP GET-slutpunkt för att hämta alla tjänster som tillhör en viss grupp/by.
     * Exempel: GET /api/groups/1/services
     *
     * @param groupId Hämtas från URL-sökvägen ({groupId})
     * @return En lista med ServiceDTO och HTTP-status 200 OK
     */
    @GetMapping
    public ResponseEntity<List<ServiceDTO>> getServicesForGroup(@PathVariable Long groupId) {
        // Anropar servicen för att hämta listan med tjänster
        List<ServiceDTO> services = serviceService.getServicesByGroup(groupId);

        // Returnerar listan med statuskod 200 OK
        return ResponseEntity.ok(services);
    }

    /**
     * HTTP POST-slutpunkt för att skapa en ny tjänst i en viss grupp.
     * Exempel: POST /api/groups/1/services?userId=2
     *
     * @param groupId Hämtas från URL-sökvägen ({groupId})
     *
     * @param dto JSON-datan i anropets body som valideras automatisk via @Valid
     * @return Den skapade tjänsten (ServiceDTO) och HTTP-status 201 Created
     */
    @PostMapping
    public ResponseEntity<ServiceDTO> createService(
            @PathVariable Long groupId,
            @Valid @RequestBody CreateServiceDTO dto) {

        User currentUser = authorizationService.getCurrentUser();
        ServiceDTO createdService = serviceService.createService(groupId, currentUser.getId(), dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdService);
    }
}
