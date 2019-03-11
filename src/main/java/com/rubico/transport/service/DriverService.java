package com.rubico.transport.service;


import com.rubico.transport.domain.Driver;
import com.rubico.transport.domain.Training;
import com.rubico.transport.repository.DriverRepository;
import com.rubico.transport.repository.EventRepository;
import com.rubico.transport.repository.TrainingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
@Transactional
public class DriverService {

    private final Logger log = LoggerFactory.getLogger(DriverService.class);
    private final DriverRepository driverRepository;
    private final TrainingRepository trainingRepository;
    private final EventRepository eventRepository;

    @Autowired
    public DriverService(TrainingRepository trainingRepository, DriverRepository driverRepository, EventRepository eventRepository) {
        this.trainingRepository = trainingRepository;
        this.driverRepository = driverRepository;
        this.eventRepository = eventRepository;
    }

    public Driver save(Driver driver) {
        //log.trace("***Start: Save driver***");
        try {
            return driverRepository.save(driver);
        } catch (Exception e) {
            //log.error("save driver failed");
        }
        return null;
    }

    public void register(Long id, String trainingTitle) {
        //log.trace("***Start: Register driver to training***");
        try {
            if (id == null || trainingTitle == null) {
                throw new Exception("id number or training title must not be null");
            }
            Optional<Driver> optDriver = driverRepository.findById(id);
            Driver driver = null;
            if (optDriver.isPresent()) {
                driver = driverRepository.findById(id).get();
            }
            if (driver == null) {
                throw new Exception("Driver not found");
            }
            Training training = trainingRepository.findByTitle(trainingTitle);
            training.addDriver(driver);
            trainingRepository.save(training);
            //log.trace("***Finish: Register driver to training***");
        } catch (Exception e) {
            log.error("register driver to training failed");
        }
    }

    public Integer calculateCompanyBalance(Date start, Date end) {
        //log.trace("***Start: calculate company balance bonus for all employee***");
        try {
            Integer fine = eventRepository.calcByStartDateBetween(start, end);
            Integer bonus = trainingRepository.countByStartDateBetween(start, end);
            return (fine + bonus) * 100;
        } catch (Exception e) {
            log.error("calc company bonus balance failed");
        }
        return null;
    }
}
