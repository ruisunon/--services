package io.cita.app.service;

import io.cita.app.model.dto.SaloonTagDto;

import java.util.List;

public interface SaloonTagService {
	
	List<SaloonTagDto> findAllBySaloonId(final Integer saloonId);
	
}


