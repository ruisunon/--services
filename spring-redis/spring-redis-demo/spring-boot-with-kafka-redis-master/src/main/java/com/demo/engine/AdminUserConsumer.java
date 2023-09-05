package com.demo.engine;

import com.demo.model.AdminUser;
import com.demo.repository.AdminUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

import java.io.IOException;

public class AdminUserConsumer {
    private final Logger logger = LoggerFactory.getLogger(AdminUserConsumer.class);

    @Autowired
    private AdminUserRepository adminUserRepository;

    @KafkaListener(topics = "AdminUSer", groupId = "group_id_adminUser")
    public void consume(AdminUser adminUser) throws IOException {
        logger.info(String.format("#### -> Consumed message -> %s", adminUser));

        //save data value to database
        this.adminUserRepository.save(adminUser);

        logger.info(String.format("#### -> ID message -> %s", adminUser.getAdmin_id()));

    }
}
