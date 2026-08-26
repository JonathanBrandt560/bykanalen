package se.JonathanAnton.bykanalen.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.JonathanAnton.bykanalen.dto.CreateServiceDTO;
import se.JonathanAnton.bykanalen.dto.ServiceDetailDTO;
import se.JonathanAnton.bykanalen.dto.ServiceSummaryDTO;
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

    /**
     * HTTP GET-slutpunkt för att hämta alla tjänster som tillhör en viss grupp/by.
     * Exempel: GET /api/groups/1/services
     *
     * @param groupId Hämtas från URL-sökvägen ({groupId})
     * @return En lista med ServiceDTO och HTTP-status 200 OK
     */
    @GetMapping
    public ResponseEntity<List<ServiceSummaryDTO>> getServicesForGroup(@PathVariable Long groupId) {
        // Returnerar listan med statuskod 200 OK
        return ResponseEntity.ok(serviceService.getServicesByGroup(groupId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceDetailDTO> getServiceById(@PathVariable Long groupId, @PathVariable Long id) {
        return ResponseEntity.ok(serviceService.getServiceById(groupId, id));
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
    public ResponseEntity<ServiceDetailDTO> createService(
            @PathVariable Long groupId,
            @Valid @RequestBody CreateServiceDTO dto) {
        return ResponseEntity.status(201).body(
                serviceService.createService(dto, groupId)
        );
    }
}
