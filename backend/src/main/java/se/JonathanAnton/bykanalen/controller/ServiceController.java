package se.JonathanAnton.bykanalen.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.JonathanAnton.bykanalen.dto.CreateServiceDTO;
import se.JonathanAnton.bykanalen.dto.ServiceDTO;
import se.JonathanAnton.bykanalen.service.ServiceService;

import java.util.List;

@RestController
@RequestMapping("/api/groups/{groupId}/services")
public class ServiceController {

    @Autowired
    private ServiceService serviceService;

    // GET: Hämta alla tjänster för en by (t.ex. GET /api/groups/1/services)
    @GetMapping
    public ResponseEntity<List<ServiceDTO>> getServicesForGroup(@PathVariable Long groupId) {
        List<ServiceDTO> services = serviceService.getServicesByGroup(groupId);
        return ResponseEntity.ok(services);
    }

    // POST: Skapa en ny tjänst (t.ex. POST /api/groups/1/services?userId=2)
    @PostMapping
    public ResponseEntity<ServiceDTO> createService(
            @PathVariable Long groupId,
            @RequestParam Long userId,
            @Valid @RequestBody CreateServiceDTO dto) {

        // Här skickar vi med userId som andra argument!
        ServiceDTO createdService = serviceService.createService(groupId, userId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdService);
    }
}