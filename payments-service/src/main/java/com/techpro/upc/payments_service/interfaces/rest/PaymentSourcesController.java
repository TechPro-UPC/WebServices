package com.techpro.upc.payments_service.interfaces.rest;

import com.techpro.upc.payments_service.domain.model.aggregates.PaymentSource;
import com.techpro.upc.payments_service.domain.model.queries.GetAllPaymentSource;
import com.techpro.upc.payments_service.domain.model.queries.GetPaymentSourceByIdQuery;
import com.techpro.upc.payments_service.domain.services.PaymentSourceCommandService;
import com.techpro.upc.payments_service.domain.services.PaymentSourceQueryService;
import com.techpro.upc.payments_service.interfaces.rest.resources.CreatePaymentSourceResource;
import com.techpro.upc.payments_service.interfaces.rest.resources.PaymentSourceResource;
import com.techpro.upc.payments_service.interfaces.transform.CreatePaymentSourceCommandFromResourceAssembler;
import com.techpro.upc.payments_service.interfaces.transform.PaymentSourceResourceFromEntityAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/v1/payment-sources")
public class PaymentSourcesController {
    private final PaymentSourceCommandService paymentSourceCommandService;
    private final PaymentSourceQueryService paymentSourceQueryService;

    public PaymentSourcesController(PaymentSourceCommandService paymentSourceCommandService, PaymentSourceQueryService paymentSourceQueryService) {
        this.paymentSourceCommandService = paymentSourceCommandService;
        this.paymentSourceQueryService = paymentSourceQueryService;
    }

    @PostMapping
    public ResponseEntity<PaymentSourceResource> createPaymentSource(@RequestBody CreatePaymentSourceResource resource) {
        Optional<PaymentSource> paymentSource = paymentSourceCommandService.handle(CreatePaymentSourceCommandFromResourceAssembler.toCommandFromResource(resource));
        return paymentSource.map(source -> new ResponseEntity<>(PaymentSourceResourceFromEntityAssembler.toResourceFromEntity(source), CREATED)).orElseGet(() -> ResponseEntity.badRequest().build());

    }

    @GetMapping("{id}")
    public ResponseEntity<PaymentSourceResource> getPaymentSourceById(@PathVariable Long id){
        Optional<PaymentSource> paymentSource = paymentSourceQueryService.handle(new GetPaymentSourceByIdQuery(id));
        return paymentSource.map(source -> ResponseEntity.ok(PaymentSourceResourceFromEntityAssembler.toResourceFromEntity(source))).orElseGet( () -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<PaymentSourceResource>> getAllPayments(){
        var payments = paymentSourceQueryService.handle(new GetAllPaymentSource());
        if (payments.isEmpty()) return ResponseEntity.notFound().build();
        var resources = payments.stream()
                .map(PaymentSourceResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }

}


