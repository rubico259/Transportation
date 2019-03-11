package com.rubico.transport.repository;

import com.rubico.transport.domain.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface TrainingRepository extends JpaRepository<Training, Long> {

    Training findByTitle(String title);

    Integer countByStartDateBetween(Date start, Date end);
}
