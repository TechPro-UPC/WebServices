package com.techpro.upc.profiles_service.interfaces.rest;

import feign.FeignException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {

    // --- 404 rutas/estáticos inexistentes (ej. favicon) ---
    // No “oculta” nada: responde 404 claro en JSON.
    @ExceptionHandler(org.springframework.web.servlet.resource.NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleNoResource(org.springframework.web.servlet.resource.NoResourceFoundException ex) {
        return Map.of(
                "error", "Not Found",
                "path", ex.getResourcePath()
        );
    }

    // --- Validaciones de entrada ---
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidation(MethodArgumentNotValidException ex) {
        var fe = ex.getBindingResult().getFieldError();
        var msg = (fe != null) ? fe.getField() + ": " + fe.getDefaultMessage() : "Validation error";
        return Map.of("error", msg);
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleBind(BindException ex) {
        var fe = ex.getFieldError();
        var msg = (fe != null) ? fe.getField() + ": " + fe.getDefaultMessage() : "Bind error";
        return Map.of("error", msg);
    }

    // --- Reglas de negocio / argumentos inválidos (tú las lanzas) ---
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleIllegalArgument(IllegalArgumentException ex) {
        return Map.of("error", ex.getMessage());
    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> handleIllegalState(IllegalStateException ex) {
        return Map.of("error", ex.getMessage());
    }

    // --- Violaciones de constraints (clave única, FK, etc.) ---
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleSql(DataIntegrityViolationException ex) {
        // Muestra el mensaje específico de la base (sirve para corregir la causa real)
        var root = ex.getMostSpecificCause() != null ? ex.getMostSpecificCause().getMessage() : ex.getMessage();
        return Map.of("error", "Constraint violation", "details", root);
    }

    // --- Errores que vienen de IAM vía Feign: NO los escondemos ---
    @ExceptionHandler(FeignException.Unauthorized.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Map<String, String> handle401(FeignException ex) {
        return Map.of("error", "Unauthorized from IAM", "details", ex.contentUTF8());
    }

    @ExceptionHandler(FeignException.Forbidden.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Map<String, String> handle403(FeignException ex) {
        return Map.of("error", "Forbidden from IAM", "details", ex.contentUTF8());
    }

    @ExceptionHandler(FeignException.NotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handle404(FeignException ex) {
        return Map.of("error", "User not found in IAM", "details", ex.contentUTF8());
    }

    @ExceptionHandler(FeignException.BadRequest.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handle400(FeignException ex) {
        return Map.of("error", "Bad request to IAM", "details", ex.contentUTF8());
    }

    // IAM devolvió 5xx: lo exponemos como 502 (upstream failure) con el cuerpo real
    @ExceptionHandler({FeignException.InternalServerError.class, FeignException.ServiceUnavailable.class, FeignException.GatewayTimeout.class})
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    public Map<String, String> handle5xxUpstream(FeignException ex) {
        String body = ex.responseBody()
                .map(b -> StandardCharsets.UTF_8.decode(b).toString())
                .orElse(ex.getMessage());
        return Map.of("error", "Upstream IAM error", "details", body);
    }

    // --- Fallback (último recurso): NO ocultes, devuelve mensaje claro ---
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> handleGeneric(Exception ex) {
        return Map.of("error", "Internal Server Error", "details", ex.getClass().getSimpleName() + ": " + ex.getMessage());
    }
}
