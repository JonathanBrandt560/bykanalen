package se.JonathanAnton.bykanalen.mapper;

import se.JonathanAnton.bykanalen.dto.ServiceDTO;

public class ServiceMapper {

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
