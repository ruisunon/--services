package io.cita.app.mapper;

import io.cita.app.model.domain.entity.Tag;
import io.cita.app.model.dto.TagDto;
import lombok.NonNull;

public interface TagMapper {
	
	public static TagDto toDto(@NonNull Tag tag) {
		return TagDto.builder()
				.id(tag.getId())
				.identifier(tag.getIdentifier())
				.name(tag.getName())
				.description(tag.getDescription())
				.build();
	}
	
}



