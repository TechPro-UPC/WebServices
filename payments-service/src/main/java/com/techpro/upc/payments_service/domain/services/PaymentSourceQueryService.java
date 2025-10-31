package com.techpro.upc.payments_service.domain.services;

import com.techpro.upc.payments_service.domain.model.aggregates.PaymentSource;
import com.techpro.upc.payments_service.domain.model.queries.GetAllPaymentSource;
import com.techpro.upc.payments_service.domain.model.queries.GetPaymentSourceByIdQuery;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface PaymentSourceQueryService {
    List<PaymentSource> handle (GetAllPaymentSource query);
    Optional<PaymentSource> handle (GetPaymentSourceByIdQuery query);

}
