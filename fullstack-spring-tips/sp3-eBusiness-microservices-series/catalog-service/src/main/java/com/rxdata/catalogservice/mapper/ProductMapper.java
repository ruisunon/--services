/*** Licensed under Apache-2.0 2021-2023 ***/
package com.rxdata.catalogservice.mapper;

import com.rxdata.catalogservice.entities.Product;
import com.rxdata.common.dtos.ProductDto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "inStock", ignore = true)
    Product toEntity(ProductDto productDto);
}
