/* Licensed under Apache-2.0 2021-2022 */
package com.example.orderservice;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.orderservice.common.AbstractIntegrationTest;

import org.junit.jupiter.api.Test;

class OrderServiceApplicationIntegrationTest extends AbstractIntegrationTest {

    @Test
    void contextLoads() {
        assertThat(POSTGRE_SQL_CONTAINER.isRunning()).isTrue();
        assertThat(KAFKA_CONTAINER.isRunning()).isTrue();
    }
}
