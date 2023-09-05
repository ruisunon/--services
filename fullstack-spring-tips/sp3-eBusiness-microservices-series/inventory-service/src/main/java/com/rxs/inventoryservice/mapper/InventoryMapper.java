/* Licensed under Apache-2.0 2021-2022 */
package com.rxs.inventoryservice.mapper;

import com.rxs.inventoryservice.dtos.InventoryDto;
import com.rxs.inventoryservice.entities.Inventory;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InventoryMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "reservedItems", ignore = true)
    Inventory toEntity(InventoryDto inventoryDto);
}
