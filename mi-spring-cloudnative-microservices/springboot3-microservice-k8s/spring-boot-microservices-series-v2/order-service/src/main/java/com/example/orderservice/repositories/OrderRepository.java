/* Licensed under Apache-2.0 2021-2022 */
package com.example.orderservice.repositories;

import com.example.orderservice.entities.Order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    // @Query("select o from Order o join fetch o.items oi ")
    @EntityGraph(attributePaths = {"items"})
    Page<Order> findAll(Pageable pageable);

    @Query("select o from Order o join fetch o.items oi where o.id = :id")
    Optional<Order> findOrderById(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query("update Order o set o.status =:status, o.source =:source where o.id = :id")
    int updateOrderStatusAndSourceById(
            @Param("id") Long orderId,
            @Param("status") String status,
            @Param("source") String source);
}
