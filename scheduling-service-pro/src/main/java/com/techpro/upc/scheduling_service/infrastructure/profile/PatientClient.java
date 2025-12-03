package com.techpro.upc.scheduling_service.infrastructure.profile;

import com.techpro.upc.scheduling_service.infrastructure.profile.resources.PatientResource;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "PROFILES-SERVICE", contextId = "patientClient")
public interface PatientClient {

    @GetMapping("/api/v1/patients/{id}")
    PatientResource getById(@PathVariable("id") Long id);
}
