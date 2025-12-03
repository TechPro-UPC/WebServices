package com.techpro.upc.payments_service.domain.model.aggregates;

import com.techpro.upc.payments_service.domain.model.commands.CreatePaymentSourceCommand;
import com.techpro.upc.payments_service.domain.valueObjects.PaymentStatus;
import com.techpro.upc.payments_service.domain.valueObjects.Money;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@Table(name = "payment_sources")
public class PaymentSource extends AbstractAggregateRoot<PaymentSource> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ⚠️ NUEVO: Necesitamos saber quién pagó para vincularlo a la reserva
    @Column(nullable = false)
    private Long userId;

    @Embedded
    private Money totalAmount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    protected PaymentSource() {}

    public PaymentSource(CreatePaymentSourceCommand command) {
        this.userId = command.userId(); // Asegúrate de que tu Command tenga userId()
        this.totalAmount = command.totalAmount(); // Asumiendo que el assembler construye el Money
        // SIMULACIÓN: El pago siempre es exitoso
        this.status = PaymentStatus.DONE;
    }
}
