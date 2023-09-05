package com.demo.engine;

import com.demo.model.Employer;
import com.demo.repository.EmployerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

import java.io.IOException;

public class EmployerConsumer {
    private final Logger logger = LoggerFactory.getLogger(EmployerConsumer.class);

    @Autowired
    private EmployerRepository employerRepository;

    @KafkaListener(topics = "employer", groupId = "group_id_employer")
    public void consume(Employer employer) throws IOException {
        logger.info(String.format("#### -> Consumed message -> %s", employer));

        //save data value to database
        this.employerRepository.save(employer);


        logger.info(String.format("#### -> ID message -> %s", employer.getEmployer_id()));
    }
}
