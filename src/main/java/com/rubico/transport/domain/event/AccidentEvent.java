package com.rubico.transport.domain.event;

import com.rubico.transport.domain.Driver;
import com.rubico.transport.domain.Vehicle;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Getter
@Setter
@Entity
@DiscriminatorValue(value = "AccidentEvent")
public class AccidentEvent extends Event {

    @OneToOne
    @JoinColumn(name = "driver_involved_id")
    private Driver driverInvolved;
    @OneToOne
    @JoinColumn(name = "vehicle_involved_id")
    private Vehicle vehicleInvolved;
}
