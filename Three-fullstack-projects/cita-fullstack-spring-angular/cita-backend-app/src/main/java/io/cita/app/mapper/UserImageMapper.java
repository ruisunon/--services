package io.cita.app.mapper;

import io.cita.app.model.domain.entity.UserImage;
import io.cita.app.model.dto.UserImageDto;
import lombok.NonNull;

public interface UserImageMapper {
	
	public static UserImageDto toDto(@NonNull final UserImage userImage) {
		return UserImageDto.builder()
				.id(userImage.getId())
				.identifier(userImage.getIdentifier())
				.name(userImage.getName())
				.type(userImage.getType())
				.size(userImage.getSize())
				.imageLob(userImage.getImageLob())
				.build();
	}
	
}



