/* Licensed under Apache-2.0 2021-2022 */
package com.rxs.inventoryservice;

import static com.rxs.inventoryservice.common.DBContainerInitializer.POSTGRE_SQL_CONTAINER;

import static org.assertj.core.api.Assertions.assertThat;

import com.rxs.inventoryservice.common.AbstractIntegrationTest;

import org.junit.jupiter.api.Test;

class InventoryServiceApplicationIntegrationTest extends AbstractIntegrationTest {

    @Test
    void contextLoads() {
        assertThat(POSTGRE_SQL_CONTAINER.isRunning()).isTrue();
    }
}
