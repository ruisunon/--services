package com.demo.engine;

import com.demo.model.Employee;
import com.demo.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

import java.io.IOException;

public class EmployeeConsumer {
    private final Logger logger = LoggerFactory.getLogger(EmployeeConsumer.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @KafkaListener(topics = "employee", groupId = "group_id_employee")
    public void consume(Employee employee) throws IOException {
        logger.info(String.format("#### -> Consumed message -> %s", employee));

        //save data value to database
        this.employeeRepository.save(employee);

        logger.info(String.format("#### -> ID message -> %s", employee.getEmployee_id()));
    }
}
