package com.demo.engine;

import com.demo.model.Jobs;
import com.demo.repository.JobsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

import java.io.IOException;

public class JobsConsumer {
    private final Logger logger = LoggerFactory.getLogger(JobsConsumer.class);

    @Autowired
    private JobsRepository jobsRepository;

    @KafkaListener(topics = "jobs", groupId = "group_id_jobs")
    public void consume(Jobs jobs) throws IOException {
        logger.info(String.format("#### -> Consumed message -> %s", jobs));

        //save data value to database
        this.jobsRepository.save(jobs);

        logger.info(String.format("#### -> ID message -> %s", jobs.getJob_id()));
    }
}
