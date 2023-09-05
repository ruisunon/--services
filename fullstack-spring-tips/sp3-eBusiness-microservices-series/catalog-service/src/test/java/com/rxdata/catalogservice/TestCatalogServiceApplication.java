/*** Licensed under Apache-2.0 2023 ***/
package com.rxdata.catalogservice;

import com.rxdata.catalogservice.common.MyContainersConfiguration;

import org.springframework.boot.SpringApplication;

public class TestCatalogServiceApplication {

    public static void main(String[] args) {
        SpringApplication.from(CatalogServiceApplication::main)
                .with(MyContainersConfiguration.class)
                .run(args);
    }
}
