package com.techpro.upc.scheduling_service.infrastructure.profile;

import com.techpro.upc.scheduling_service.infrastructure.profile.resources.PsychologistResource;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "PROFILES-SERVICE", contextId = "psychologistClient")
public interface PsychologistClient {

    @GetMapping("/api/v1/psychologists/{psychologistId}")
    ResponseEntity<PsychologistResource> getById(@PathVariable("psychologistId") Long psychologistId);

    @GetMapping("/api/v1/psychologists/user/{userId}")
    ResponseEntity<PsychologistResource> getByUserId(@PathVariable("userId") Long userId);
}
