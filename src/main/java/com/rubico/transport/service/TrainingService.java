package com.rubico.transport.service;


import com.rubico.transport.domain.Training;
import com.rubico.transport.repository.TrainingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TrainingService {

    private final Logger log = LoggerFactory.getLogger(TrainingService.class);
    private final TrainingRepository repository;

    @Autowired
    public TrainingService(TrainingRepository repository){
        this.repository = repository;
    }

    public Training save(Training training){
        //log.trace("***Start: save training***");
        try {
            return repository.save(training);
        } catch (Exception e) {
            log.error("save training failed");
        }
        return null;
    }
}
