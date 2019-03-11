package com.rubico.transport.rest;


import com.rubico.transport.domain.Driver;
import com.rubico.transport.service.DriverService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Slf4j
@RestController
@RequestMapping("/api")
public class DriverResource {


    private final DriverService driverService;

    @Autowired
    public DriverResource(DriverService driverService) {
        this.driverService = driverService;
    }

    @PostMapping("/drivers")
    public ResponseEntity<Driver> addDriver(@Valid @RequestBody Driver driver) throws Exception {
        if (driver.getId() != null) {
            throw new Exception("A new driver cannot already have an ID");
        }
        //log.debug("REST request to add driver");
        Driver result = driverService.save(driver);
        if (result == null) {
            throw new EntityNotFoundException("Result cannot be null, Something went wrong");
        }
        return ResponseEntity.ok()
                .body(result);
    }

    @PutMapping("/drivers/register")
    public ResponseEntity<Void> registerToTraining(@RequestBody @NotNull Long id, @NotNull String trainingTitle) {
        //log.debug("REST request to register driver to driver training : {}{}", id, trainingTitle);
        driverService.register(id, trainingTitle);
        return ResponseEntity.ok().build();
    }

    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss.SSS")
    @GetMapping("/drivers/bonus")
    public ResponseEntity<Integer> calcBonus(@RequestParam @NotNull Date start, @RequestParam @NotNull Date end) throws Exception {
        //log.trace("REST request to calculate company bonus balance");
        Integer bonus = driverService.calculateCompanyBalance(start, end);
        if (bonus == null) {
            throw new Exception("Something went wrong");
        }
        return ResponseEntity.ok().body(bonus);
    }
}
