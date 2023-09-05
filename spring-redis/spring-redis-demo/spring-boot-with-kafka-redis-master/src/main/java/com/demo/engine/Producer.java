package com.demo.engine;

import com.demo.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class Producer {

    private static final Logger logger = LoggerFactory.getLogger(Producer.class);
    private static final String TOPIC1 = "employer";
    private static final String TOPIC2 = "employee";
    private static final String TOPIC3 = "adminUser";
    private static final String TOPIC4 = "categories";
    private static final String TOPIC5 = "jobs";

    @Autowired
    private KafkaTemplate<String, Employer> kafkaTemplate1;

    @Autowired
    private KafkaTemplate<String, Employee> kafkaTemplate2;

    @Autowired
    private KafkaTemplate<String, AdminUser> kafkaTemplate3;

    @Autowired
    private KafkaTemplate<String, JobCategories> kafkaTemplate4;

    @Autowired
    private KafkaTemplate<String, Jobs> kafkaTemplate5;

    public void sendMessage1(Employer employer) {
        logger.info(String.format("#### -> Producing message -> %s", employer.getEmployer_id()));
        this.kafkaTemplate1.send(TOPIC1, employer);
    }

    public void sendMessage2(Employee employee) {
        logger.info(String.format("#### -> Producing message -> %s", employee.getEmployee_id()));
        this.kafkaTemplate2.send(TOPIC2, employee);
    }
    public void sendMessage3(AdminUser adminUser) {
        logger.info(String.format("#### -> Producing message -> %s", adminUser.getAdmin_id()));
        this.kafkaTemplate3.send(TOPIC3, adminUser);
    }

    public void sendMessage4(JobCategories jobCategories) {
        logger.info(String.format("#### -> Producing message -> %s", jobCategories.getCategory_id()));
        this.kafkaTemplate4.send(TOPIC4, jobCategories);
    }

    public void sendMessage5(Jobs jobs) {
        logger.info(String.format("#### -> Producing message -> %s", jobs.getJob_id()));
        this.kafkaTemplate5.send(TOPIC5, jobs);
    }
}
