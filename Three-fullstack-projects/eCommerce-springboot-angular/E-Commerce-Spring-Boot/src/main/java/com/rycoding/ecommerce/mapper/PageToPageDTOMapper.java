package com.rycoding.ecommerce.mapper;

import com.rycoding.ecommerce.dto.PageDTO;
import org.springframework.data.domain.Page;

public class PageToPageDTOMapper<T> {
    public PageDTO<T> pageToPageDTO(Page<T> page) {
        PageDTO<T> pageDTO = new PageDTO<>();
        pageDTO.setContent(page.getContent());
        pageDTO.setTotalPages(page.getTotalPages());
        pageDTO.setTotalElements(page.getTotalElements());
        pageDTO.setPageNumber(page.getNumber());
        pageDTO.setPageSize(page.getSize());
        return pageDTO;
    }
}
