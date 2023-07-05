package io.cita.app.service.impl;

import io.cita.app.mapper.SaloonTagMapper;
import io.cita.app.model.dto.SaloonTagDto;
import io.cita.app.service.SaloonTagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import io.cita.app.repository.SaloonTagRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class SaloonTagServiceImpl implements SaloonTagService {
	
	private final SaloonTagRepository saloonTagRepository;
	
	@Override
	public List<SaloonTagDto> findAllBySaloonId(final Integer saloonId) {
		log.info("** Find all saloonTags by saloonId.. *");
		return this.saloonTagRepository
				.findAllBySaloonId(saloonId).stream()
					.map(SaloonTagMapper::toDto)
					.toList();
	}
	
}



