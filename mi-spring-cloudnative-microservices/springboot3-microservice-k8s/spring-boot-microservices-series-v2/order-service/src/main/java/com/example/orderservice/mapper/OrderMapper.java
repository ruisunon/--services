/* Licensed under Apache-2.0 2021-2022 */
package com.example.orderservice.mapper;

import com.example.common.dtos.OrderDto;
import com.example.common.dtos.OrderItemDto;
import com.example.orderservice.entities.Order;
import com.example.orderservice.entities.OrderItem;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;

import java.util.function.Consumer;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface OrderMapper {

    @Mapping(source = "id", target = "orderId")
    OrderDto toDto(Order order);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "items", ignore = true)
    Order toEntity(OrderDto orderDto);

    @Mapping(target = "itemId", source = "id")
    OrderItemDto orderItemToOrderItemDto(OrderItem orderItem);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "order", ignore = true)
    OrderItem orderItemDtoToOrderItem(OrderItemDto orderItemDto);

    @AfterMapping
    default void addOrderItemToOrderEntity(OrderDto orderDTO, @MappingTarget Order order) {
        Consumer<OrderItemDto> addOrderItemToOrder =
                orderItemDTO -> order.addOrderItem(orderItemDtoToOrderItem(orderItemDTO));
        orderDTO.getItems().forEach(addOrderItemToOrder);
    }
}
