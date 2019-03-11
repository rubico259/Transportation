package com.rubico.transport.rest;


import com.rubico.transport.domain.Vehicle;
import com.rubico.transport.service.VehicleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class VehicleResource {

    private final Logger log = LoggerFactory.getLogger(VehicleResource.class);
    private final VehicleService vehicleService;

    @Autowired
    public VehicleResource(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping("/vehicle")
    public ResponseEntity<Vehicle> addVehicle(@Valid @RequestBody Vehicle vehicle) throws Exception {
        if (vehicle.getId() != null) {
            throw new Exception("A new vehicle cannot already have an ID");
        }
        //log.trace("REST request to add vehicle with name: {}", vehicle.getName());

        Vehicle result = vehicleService.save(vehicle);
        if (result == null) {
            throw new EntityNotFoundException();
        }
        return ResponseEntity.ok()
                .body(result);
    }
}
