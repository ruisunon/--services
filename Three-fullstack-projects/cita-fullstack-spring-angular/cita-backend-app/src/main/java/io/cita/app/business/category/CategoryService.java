package io.cita.app.business.category;

import io.cita.app.model.dto.CategoryDto;
import io.cita.app.business.category.employee.manager.model.CategoryRequest;

import java.util.List;

public interface CategoryService {
	
	List<CategoryDto> findAll();
	CategoryDto findById(final Integer id);
	CategoryDto findByIdentifier(final String identifier);
	List<CategoryDto> findAllBySaloonId(final Integer saloonId);
	CategoryDto save(final CategoryRequest categoryRequest);
	CategoryDto update(final CategoryRequest categoryRequest);
	
}



