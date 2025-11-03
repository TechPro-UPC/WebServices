package com.techpro.upc.profiles_service.infrastructure.iam;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "iam-client",
        url  = "${iam.base-url}",
        configuration = FeignAuthConfig.class
)
public interface IamClient {

    @GetMapping("/api/v1/users/{id}")
    UserResource getUserById(@PathVariable("id") Long id);
}
