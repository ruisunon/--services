/* Licensed under Apache-2.0 2022-2023 */
package com.rxdata.paymentservice.config;

import com.rxdata.common.dtos.OrderDto;
import com.rxdata.paymentservice.services.OrderManageService;
import com.rxdata.paymentservice.utils.AppConstants;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
@EnableKafka
public class KafkaListenerConfig {

    private final OrderManageService orderManageService;

    @KafkaListener(id = "orders", topics = AppConstants.ORDERS_TOPIC, groupId = "payment")
    public void onEvent(OrderDto orderDto) {
        log.info("Received Order in payment service: {} from topic: {}", orderDto, AppConstants.ORDERS_TOPIC);
        if ("NEW".equals(orderDto.getStatus())) {
            orderManageService.reserve(orderDto);
        } else {
            orderManageService.confirm(orderDto);
        }
    }
}
