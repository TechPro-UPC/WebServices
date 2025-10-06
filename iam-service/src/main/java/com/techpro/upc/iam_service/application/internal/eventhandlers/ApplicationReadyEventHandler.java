package com.techpro.upc.iam_service.application.internal.eventhandlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;


@Service
public class ApplicationReadyEventHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationReadyEventHandler.class);


    @EventListener
    public void on(ApplicationReadyEvent event) {
        var applicationName = event.getApplicationContext().getId();
        LOGGER.info("Application ready for {} at {}", applicationName, currentTimestamp());
        LOGGER.info("Application startup finished for {} at {}", applicationName, currentTimestamp());
    }

    private Timestamp currentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }
}