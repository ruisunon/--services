package io.cita.app.service;

import io.cita.app.model.dto.TagDto;
import io.cita.app.model.dto.request.ClientPageRequest;
import org.springframework.data.domain.Page;

public interface TagService {
	
	Page<TagDto> findAll(final ClientPageRequest clientPageRequest);
	TagDto findById(final Integer id);
	TagDto findByIdentifier(final String identifier);
	
}



