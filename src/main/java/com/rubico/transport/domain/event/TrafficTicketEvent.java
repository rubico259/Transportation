package com.rubico.transport.domain.event;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@DiscriminatorValue(value = "TrafficTicketEvent")
public class TrafficTicketEvent extends Event {

    @Column
    private BigDecimal fineAmount;
    @Column
    private TrafficTicketCause cause;
}
