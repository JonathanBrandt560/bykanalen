package se.JonathanAnton.bykanalen.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.security.access.AccessDeniedException;

import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {
    // Resursen hittades inte
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse>
    handleNotFound(ResourceNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(404, ex.getMessage());
        return ResponseEntity.status(404).body(error);
    }

    // Åtkomst nekad - användaren saknar rätt behörighet
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDenied(AccessDeniedException ex) {
        ErrorResponse error = new ErrorResponse(403, ex.getMessage());
        return ResponseEntity.status(403).body(error);
    }

    // Valideringsfel - @Valid mysslyckades
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> fieldErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        FieldError::getDefaultMessage,
                        (existing, replacement) -> existing // behåll första felet om ett fält har flera
                ));

        String message = String.join(", ", fieldErrors.values());

        ErrorResponse error = new ErrorResponse(400, message, fieldErrors);
        return ResponseEntity.status(400).body(error);
    }

    // Felaktigt format på pathvariabel från klient
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse>
    handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String message = "Ogiltigt värde för parametern: " + ex.getName();
        return ResponseEntity.badRequest().body(new ErrorResponse(400, message));
    }

    // Oväntade fel
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneral(Exception ex) {
        ex.printStackTrace();
        ErrorResponse error = new ErrorResponse(500, ex.getMessage());
        return ResponseEntity.status(500).body(error);
    }
}
