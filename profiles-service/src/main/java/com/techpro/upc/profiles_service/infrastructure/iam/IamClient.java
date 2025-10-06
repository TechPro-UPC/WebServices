package com.techpro.upc.profiles_service.infrastructure.iam;

import com.techpro.upc.iam_service.interfaces.rest.resources.UserResource;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Feign Client para comunicarse con el microservicio IAM.
 * Este cliente permite consultar usuarios desde profiles-service.
 */
@FeignClient(
        name = "iam-service",
        url = "http://localhost:8080/api/v1/users" // cambia el puerto si tu IAM usa otro
)
public interface IamClient {

    @GetMapping("/{id}")
    ResponseEntity<UserResource> getUserById(@PathVariable("id") Long id);
}
