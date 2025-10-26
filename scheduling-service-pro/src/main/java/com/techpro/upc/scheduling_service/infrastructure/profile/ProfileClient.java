package com.techpro.upc.scheduling_service.infrastructure.profile;

import com.techpro.upc.scheduling_service.infrastructure.profile.resources.PatientResource;
import com.techpro.upc.scheduling_service.infrastructure.profile.resources.PsychologistResource;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Cliente Feign para comunicarse con Profiles Service.
 * Usa el base-url configurable via `profiles-service.url`.
 */
@FeignClient(
        name = "profiles-service",
        contextId = "profileClient",              // ✅ agregado
        url = "${profiles-service.url}"
)
public interface ProfileClient {

    @GetMapping("/api/v1/patients/user/{userId}")
    ResponseEntity<PatientResource> getPatientByUserId(@PathVariable("userId") Long userId);

    @GetMapping("/api/v1/psychologists/user/{userId}")
    ResponseEntity<PsychologistResource> getPsychologistByUserId(@PathVariable("userId") Long userId);
}
