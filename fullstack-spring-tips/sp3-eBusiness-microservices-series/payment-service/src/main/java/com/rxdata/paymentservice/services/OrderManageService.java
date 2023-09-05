/* Licensed under Apache-2.0 2022-2023 */
package com.rxdata.paymentservice.services;

import com.rxdata.common.dtos.OrderDto;
import com.rxdata.common.dtos.OrderItemDto;
import com.rxdata.paymentservice.entities.Customer;
import com.rxdata.paymentservice.repositories.CustomerRepository;
import com.rxdata.paymentservice.utils.AppConstants;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderManageService {

    private final CustomerRepository customerRepository;
    private final KafkaTemplate<String, OrderDto> kafkaTemplate;

    public void reserve(OrderDto orderDto) {
        log.debug("Reserving Order in payment Service {}", orderDto);
        Customer customer = customerRepository.findById(orderDto.getCustomerId()).orElseThrow();
        log.info("Found Customer: {}", customer.getId());
        var totalOrderPrice = orderDto.getItems().stream()
                        .map(OrderItemDto::getPrice)
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
                        .intValue();
        if (totalOrderPrice < customer.getAmountAvailable()) {
            orderDto.setStatus("ACCEPT");
            customer.setAmountReserved(customer.getAmountReserved() + totalOrderPrice);
            customer.setAmountAvailable(customer.getAmountAvailable() - totalOrderPrice);
        } else {
            orderDto.setStatus("REJECT");
        }
        orderDto.setSource(AppConstants.SOURCE);
        log.info("Saving customer after reserving:{}", customer.getId());
        customerRepository.save(customer);
        kafkaTemplate.send(AppConstants.PAYMENT_ORDERS_TOPIC, String.valueOf(orderDto.getOrderId()), orderDto);
        log.info("Sent Reserved Order: {}", orderDto);
    }

    public void confirm(OrderDto orderDto) {
        log.debug("Confirming Order in payment service {}", orderDto);
        Customer customer = customerRepository.findById(orderDto.getCustomerId()).orElseThrow();
        log.info("Found Customer: {}", customer.getId());
        var orderAmount = orderDto.getItems().stream()
                        .map(OrderItemDto::getQuantity)
                        .reduce(0, Integer::sum).intValue();
        if (orderDto.getStatus().equals("CONFIRMED")) {
            customer.setAmountReserved(customer.getAmountReserved() - orderAmount);
        } else if (orderDto.getStatus().equals("ROLLBACK") && !orderDto.getSource().equals(AppConstants.SOURCE)) {
            customer.setAmountReserved(customer.getAmountReserved() - orderAmount);
            customer.setAmountAvailable(customer.getAmountAvailable() + orderAmount);
        }
        log.info("Saving customer After Confirmation:{}", customer.getId());
        customerRepository.save(customer);
    }
}
