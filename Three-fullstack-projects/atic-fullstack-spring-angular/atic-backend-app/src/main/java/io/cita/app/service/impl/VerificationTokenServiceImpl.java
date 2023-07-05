package io.cita.app.service.impl;

import io.cita.app.mapper.VerificationTokenMapper;
import io.cita.app.model.dto.VerificationTokenDto;
import io.cita.app.repository.VerificationTokenRepository;
import io.cita.app.service.VerificationTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import io.cita.app.exception.wrapper.VerificationTokenNotFoundException;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class VerificationTokenServiceImpl implements VerificationTokenService {
	
	private final VerificationTokenRepository verificationTokenRepository;
	
	@Override
	public VerificationTokenDto findByToken(final String token) {
		log.info("** Find verificationToken by token.. *");
		return this.verificationTokenRepository.findByToken(token)
				.map(VerificationTokenMapper::toDto)
				.orElseThrow(() -> new VerificationTokenNotFoundException(
						"VerificationToken with token: %s is not found".formatted(token)));
	}
	
	@Transactional
	@Override
	public boolean deleteByToken(final String token) {
		log.info("** Delete verificationToken by token..*");
		this.verificationTokenRepository.deleteByToken(token);
		return true;
	}
	
}



