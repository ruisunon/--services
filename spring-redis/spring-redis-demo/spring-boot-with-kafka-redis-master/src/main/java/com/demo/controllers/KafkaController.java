package com.demo.controllers;

import com.demo.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/kafka")
public class KafkaController {

    private static final Logger logger = LoggerFactory.getLogger(KafkaController.class);

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

    @PostMapping(value = "/employer/publish")
    public String sendMessageToKafkaTopic1(@RequestBody Employer employer) {
        logger.info(String.format("#### -> Producing message -> %s", employer.getEmployer_id()));
        this.kafkaTemplate1.send("employer", employer);
        return "Success";
    }

    @PostMapping(value = "/employee/publish")
    public String sendMessageToKafkaTopic2(@RequestBody Employee employee) {
        logger.info(String.format("#### -> Producing message -> %s", employee.getEmployee_id()));
        this.kafkaTemplate2.send("employee", employee);
        return "Success";
    }
    @PostMapping(value = "/adminUser/publish")
    public String sendMessageToKafkaTopic3(@RequestBody AdminUser adminUser) {
        logger.info(String.format("#### -> Producing message -> %s", adminUser.getAdmin_id()));
        this.kafkaTemplate3.send("adminUser", adminUser);
        return "Success";
    }
    @PostMapping(value = "/categories/publish")
    public String sendMessageToKafkaTopic4(@RequestBody JobCategories jobCategories) {
        logger.info(String.format("#### -> Producing message -> %s", jobCategories.getCategory_id()));
        this.kafkaTemplate4.send("jobCategories", jobCategories);
        return "Success";
    }

    @PostMapping(value = "/jobs/publish")
    public String sendMessageToKafkaTopic5(@RequestBody Jobs jobs) {
        logger.info(String.format("#### -> Producing message -> %s", jobs.getJob_id()));
        this.kafkaTemplate5.send("jobs", jobs);
        return "Success";
    }
}
