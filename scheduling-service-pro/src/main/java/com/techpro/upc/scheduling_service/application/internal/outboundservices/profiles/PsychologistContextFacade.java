package com.techpro.upc.scheduling_service.application.internal.outboundservices.profiles;

import com.techpro.upc.scheduling_service.infrastructure.profile.PsychologistClient;
import com.techpro.upc.scheduling_service.infrastructure.profile.resources.PsychologistResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PsychologistContextFacade {

    private final PsychologistClient psychologistClient;

    public PsychologistContextFacade(PsychologistClient psychologistClient) {
        this.psychologistClient = psychologistClient;
    }

    /**
     * Busca un psicólogo en Profiles Service por ID.
     * Lanza 404 si no existe o Profiles no responde 2xx.
     */
    public PsychologistResource fetchPsychologistById(Long psychologistId) {
        var response = psychologistClient.getById(psychologistId);
        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Psychologist with id " + psychologistId + " not found");
        }
        return response.getBody();
    }
}

