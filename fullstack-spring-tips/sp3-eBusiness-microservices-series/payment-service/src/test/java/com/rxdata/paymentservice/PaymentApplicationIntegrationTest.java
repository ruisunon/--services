/* Licensed under Apache-2.0 2022-2023 */
package com.rxdata.paymentservice;

import static org.assertj.core.api.Assertions.assertThat;

import com.rxdata.paymentservice.common.AbstractIntegrationTest;

import org.junit.jupiter.api.Test;

class PaymentApplicationIntegrationTest extends AbstractIntegrationTest {

    @Test
    void contextLoads() {
        assertThat(CONFIG_SERVER_CONTAINER.isRunning()).isTrue();
    }
}
