package com.demo.engine;


import com.demo.model.JobCategories;
import com.demo.repository.JobCategoriesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

import java.io.IOException;

public class JobCategoriesConsumer {
    private final Logger logger = LoggerFactory.getLogger(JobCategoriesConsumer.class);
    @Autowired
    private JobCategoriesRepository jobCategoriesRepository;

    @KafkaListener(topics = "categories", groupId = "group_id_categories")
    public void consume(JobCategories jobCategories) throws IOException {
        logger.info(String.format("#### -> Consumed message -> %s", jobCategories));

        //save data value to database
        this.jobCategoriesRepository.save(jobCategories);


        logger.info(String.format("#### -> ID message -> %s", jobCategories.getCategory_id()));
    }
}
