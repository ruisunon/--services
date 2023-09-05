/* Licensed under Apache-2.0 2022 */
package com.rxdata.orderservice.repositories;

import com.rxdata.orderservice.entities.OrderItem;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {}
