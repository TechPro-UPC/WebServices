package com.techpro.upc.payments_service.domain.model.aggregates;

import com.techpro.upc.payments_service.domain.model.commands.CreatePaymentSourceCommand;
import com.techpro.upc.payments_service.domain.valueObjects.PaymentStatus;
import com.techpro.upc.payments_service.domain.valueObjects.Money;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.domain.AbstractAggregateRoot;

@Entity
public class PaymentSource extends AbstractAggregateRoot<PaymentSource> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Getter
    @Embedded
    private Money totalAmount;


    @Column(nullable = false, updatable = true)
    @Enumerated(EnumType.STRING)
    @Getter
    private PaymentStatus status;

    protected PaymentSource() {}

    public PaymentSource(CreatePaymentSourceCommand command) {
        this.totalAmount = command.totalAmount();
        this.status = command.status();

    }
}
