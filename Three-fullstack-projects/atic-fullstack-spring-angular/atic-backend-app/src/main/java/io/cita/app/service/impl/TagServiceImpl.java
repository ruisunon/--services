package io.cita.app.service.impl;

import io.cita.app.mapper.TagMapper;
import io.cita.app.model.dto.TagDto;
import io.cita.app.model.dto.request.ClientPageRequest;
import io.cita.app.repository.TagRepository;
import io.cita.app.service.TagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import io.cita.app.exception.wrapper.TagNotFoundException;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
	
	private final TagRepository tagRepository;
	
	@Override
	public Page<TagDto> findAll(final ClientPageRequest clientPageRequest) {
		log.info("** Find all paged tags.. *");
		return this.tagRepository
				.findAll(PageRequest.of(
						clientPageRequest.getOffset() - 1, clientPageRequest.getSize()))
				.map(TagMapper::toDto);
	}
	
	@Override
	public TagDto findById(final Integer id) {
		log.info("** Find tag by id.. *");
		return this.tagRepository.findById(id)
				.map(TagMapper::toDto)
				.orElseThrow(TagNotFoundException::new);
	}
	
	@Override
	public TagDto findByIdentifier(final String identifier) {
		return this.tagRepository.findByIdentifier(identifier.strip())
				.map(TagMapper::toDto)
				.orElseThrow(TagNotFoundException::new);
	}
	
}



