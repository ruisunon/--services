/*** Licensed under Apache-2.0 2023 ***/
package com.rxdata.catalogservice.model.response;

import java.io.Serializable;

public record InventoryDto(String productCode, Integer availableQuantity) implements Serializable {}
