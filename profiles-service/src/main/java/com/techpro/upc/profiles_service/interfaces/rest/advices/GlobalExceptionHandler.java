package com.techpro.upc.profiles_service.interfaces.rest.advices;

import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Manejador global de excepciones para devolver errores consistentes en formato JSON.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        Map<String, String> error = new HashMap<>();
        String message = "";

        // Detectar la causa más específica
        Throwable rootCause = ex.getRootCause();
        if (rootCause != null) {
            message = rootCause.getMessage();
        } else if (ex.getMostSpecificCause() != null) {
            message = ex.getMostSpecificCause().getMessage();
        }

        if (message.contains("dni")) {
            error.put("error", "Ya existe un registro con el mismo DNI.");
        } else if (message.contains("user_id")) {
            error.put("error", "Ya existe un perfil asociado a ese usuario.");
        } else if (message.contains("license_number")) {
            error.put("error", "Ya existe un psicólogo con el mismo número de licencia.");
        } else {
            error.put("error", "Error de integridad de datos. Verifique los valores únicos.");
        }

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // ✅ NUEVO: captura errores de validación de campos (como @Size, @NotNull, etc.)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleConstraintViolationException(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(v -> {
            String field = v.getPropertyPath().toString();
            String message = v.getMessage();
            errors.put(field, message);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericException(Exception ex) {
        Map<String, String> error = new HashMap<>();

        // 🔍 Registrar la excepción para debug
        System.err.println("⚠️ Excepción no manejada: " + ex.getClass().getName());
        if (ex.getCause() != null) {
            System.err.println("Causa raíz: " + ex.getCause().getMessage());
        }

        error.put("error", "Ocurrió un error interno en el servidor.");
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
