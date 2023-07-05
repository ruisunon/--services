package io.cita.app.business.category.employee.manager.service;

import io.cita.app.model.dto.CategoryDto;
import org.springframework.data.domain.Page;
import io.cita.app.business.category.employee.manager.model.CategoryRequest;

public interface ManagerCategoryService {
	
	Page<CategoryDto> fetchAll(final String username);
	CategoryDto fetchById(final Integer categoryId);
	Boolean deleteCategory(final Integer categoryId);
	CategoryDto saveCategory(final CategoryRequest categoryRequest);
	CategoryDto updateCategory(final CategoryRequest categoryRequest);
	
}



