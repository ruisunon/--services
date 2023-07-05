package io.cita.app.mapper;

import io.cita.app.model.domain.entity.Category;
import io.cita.app.model.dto.CategoryDto;
import io.cita.app.model.dto.SaloonDto;
import lombok.NonNull;

import java.util.Objects;

public interface CategoryMapper {
	
	public static CategoryDto toDto(@NonNull final Category category) {
		
		final var parentCategory = Objects
				.requireNonNullElseGet(category.getParentCategory(), Category::new);
		
		return CategoryDto.builder()
				.id(category.getId())
				.identifier(category.getIdentifier())
				.name(category.getName())
				.parentCategoryDto(
					CategoryDto.builder()
						.id(parentCategory.getId())
						.identifier(parentCategory.getIdentifier())
						.name(parentCategory.getName())
						.build())
				.saloonDto(
					SaloonDto.builder()
						.id(category.getSaloon().getId())
						.identifier(category.getSaloon().getIdentifier())
						.code(category.getSaloon().getCode())
						.taxRef(category.getSaloon().getTaxRef())
						.name(category.getSaloon().getName())
						.isPrimary(category.getSaloon().getIsPrimary())
						.openingDate(category.getSaloon().getOpeningDate())
						.fullAdr(category.getSaloon().getFullAdr())
						.email(category.getSaloon().getEmail())
						.build())
				.build();
	}
	
}




