package com.rubico.transport.rest;


import com.rubico.transport.domain.Training;
import com.rubico.transport.service.TrainingService;
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
public class TrainingResource {

    private final Logger log = LoggerFactory.getLogger(TrainingResource.class);
    private final TrainingService trainingService;

    @Autowired
    public TrainingResource(TrainingService trainingService) {
        this.trainingService = trainingService;
    }

    @PostMapping("/training")
    public ResponseEntity<Training> addTraining(@Valid @RequestBody Training training) throws Exception {
        if (training.getId() != null) {
            throw new Exception("A new training cannot already have an ID");
        }
        //log.trace("REST request to add training with title : {}", training.getTitle());

        Training result = trainingService.save(training);
        if (result == null) {
            throw new EntityNotFoundException();
        }
        return ResponseEntity.ok()
                .body(result);
    }
}
