package com.rubico.transport.domain.event;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.rubico.transport.domain.Driver;
import com.rubico.transport.domain.Vehicle;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
        property = "type")
@JsonSubTypes({
        @Type(value = AccidentEvent.class),
        @Type(value = ParkingTicketEvent.class),
        @Type(value = TrafficTicketEvent.class),
})

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING, name = "type")
public abstract class Event implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    protected Long id;
    @OneToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.DETACH)
    @JoinColumn(name = "driver_id", nullable = false)
    private Driver driver;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @NotNull
    private Date occur;
    @NotNull
    private String city;
    @NotNull
    private String street;
    @Column(insertable = false, updatable = false)
    private String type;
}
