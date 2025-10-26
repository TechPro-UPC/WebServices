package com.techpro.upc.scheduling_service.interfaces.rest.transform;

import com.techpro.upc.scheduling_service.domain.model.commands.CreateTimeSlotCommand;
import com.techpro.upc.scheduling_service.interfaces.rest.resources.CreateTimeSlotResource;

public class CreateTimeSlotCommandFromResourceAssembler {

    public static CreateTimeSlotCommand toCommandFromResource(CreateTimeSlotResource resource) {
        return new CreateTimeSlotCommand(
                resource.startTime(),
                resource.endTime(),
                resource.psychologistId()
        );
    }
}
