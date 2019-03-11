package com.rubico.transport.service;


import com.rubico.transport.domain.Vehicle;
import com.rubico.transport.repository.VehicleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class VehicleService {

    private final Logger log = LoggerFactory.getLogger(VehicleService.class);
    private final VehicleRepository repository;

    @Autowired
    public VehicleService(VehicleRepository repository) {
        this.repository = repository;
    }

    public Vehicle save(Vehicle vehicle) {
        //log.trace("***Start: save vehicle***");
        try {
            return repository.save(vehicle);
        } catch (Exception e) {
            log.error("save vehicle failed");
        }
        return null;
    }
}
