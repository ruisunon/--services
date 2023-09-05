/* Licensed under Apache-2.0 2022 */
package com.example.inventoryservice.services;

import com.example.common.dtos.OrderDto;
import com.example.common.dtos.OrderItemDto;
import com.example.inventoryservice.entities.Inventory;
import com.example.inventoryservice.repositories.InventoryRepository;
import com.example.inventoryservice.utils.AppConstants;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class InventoryOrderManageService {

    private final InventoryRepository inventoryRepository;
    private final KafkaTemplate<String, OrderDto> kafkaTemplate;

    public void reserve(OrderDto orderDto) {
        log.info("Reserving Order in Inventory Service {}", orderDto);
        List<String> productCodeList =
                orderDto.getItems().stream().map(OrderItemDto::getProductId).toList();
        List<Inventory> inventoryList = inventoryRepository.findByProductCodeIn(productCodeList);
        if (inventoryList.size() == productCodeList.size()) {
            log.info("All Products Exists");
            if ("NEW".equals(orderDto.getStatus())) {
                List<Inventory> persistInventoryList = new ArrayList<>();

                outerLoopBreakVariable:
                for (OrderItemDto orderItemDto : orderDto.getItems()) {
                    for (Inventory inventory : inventoryList) {
                        if (inventory.getProductCode().equals(orderItemDto.getProductId())) {
                            int productCount = orderItemDto.getQuantity();
                            if (productCount < inventory.getAvailableQuantity()) {
                                inventory.setReservedItems(
                                        inventory.getReservedItems() + productCount);
                                inventory.setAvailableQuantity(
                                        inventory.getAvailableQuantity() - productCount);
                                persistInventoryList.add(inventory);
                            } else {
                                break outerLoopBreakVariable;
                            }
                            break;
                        }
                    }
                }
                if (inventoryList.size() == persistInventoryList.size()) {
                    orderDto.setStatus("ACCEPT");
                    log.info(
                            "Setting status as ACCEPT for inventoryIds : {}",
                            persistInventoryList.stream().map(inv -> inv.getId()).toList());
                    inventoryRepository.saveAll(persistInventoryList);
                } else {
                    log.info(
                            "Setting status as REJECT for OrderId in Inventory Service : {}",
                            orderDto.getOrderId());
                    orderDto.setStatus("REJECT");
                }
                kafkaTemplate.send(
                        AppConstants.STOCK_ORDERS_TOPIC,
                        String.valueOf(orderDto.getOrderId()),
                        orderDto);
                log.info(
                        "Sent Order after reserving : {} from inventory service to topic {}",
                        orderDto,
                        AppConstants.STOCK_ORDERS_TOPIC);
            }
        } else {
            log.error(
                    "Not all products requested exists, Hence Ignoring OrderID :{}",
                    orderDto.getOrderId());
        }
    }

    public void confirm(OrderDto orderDto) {
        List<String> productCodeList =
                orderDto.getItems().stream().map(OrderItemDto::getProductId).toList();
        List<Inventory> inventoryList = inventoryRepository.findByProductCodeIn(productCodeList);

        for (Inventory inventory : inventoryList) {
            for (OrderItemDto orderItemDto : orderDto.getItems()) {
                if (inventory.getProductCode().equals(orderItemDto.getProductId())) {
                    Integer productCount = orderItemDto.getQuantity();
                    if ("CONFIRMED".equals(orderDto.getStatus())) {
                        inventory.setReservedItems(inventory.getReservedItems() - productCount);
                    } else if (AppConstants.ROLLBACK.equals(orderDto.getStatus())
                            && !(AppConstants.SOURCE.equalsIgnoreCase(orderDto.getSource()))) {
                        inventory.setReservedItems(inventory.getReservedItems() - productCount);
                        inventory.setAvailableQuantity(
                                inventory.getAvailableQuantity() + productCount);
                    }
                    break;
                }
            }
        }
        inventoryRepository.saveAll(inventoryList);
        log.info(
                "Saving inventoryIds : {} After Confirmation",
                inventoryList.stream().map(inv -> inv.getId()).toList());
    }
}
