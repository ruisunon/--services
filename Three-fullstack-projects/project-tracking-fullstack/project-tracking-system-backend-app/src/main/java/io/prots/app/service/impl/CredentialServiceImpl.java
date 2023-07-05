package io.prots.app.service.impl;

import java.util.Collections;

import jakarta.transaction.Transactional;

import io.prots.app.exception.custom.ObjectNotFoundException;
import io.prots.app.repository.CredentialRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import io.prots.app.model.dto.collection.DtoCollection;
import io.prots.app.model.entity.Credential;
import io.prots.app.service.CredentialService;

@Service
@Transactional
public class CredentialServiceImpl implements CredentialService {
	
	private final CredentialRepository rep;
	private final BCryptPasswordEncoder encoder;
	private static final Logger logger = LoggerFactory.getLogger(CredentialServiceImpl.class);
	
	static {
		logger.info("************ entering " + CredentialServiceImpl.class.getName() + " ************");
	}
	
	@Autowired
	public CredentialServiceImpl(final CredentialRepository rep, final BCryptPasswordEncoder encoder) {
		this.rep = rep;
		this.encoder = encoder;
	}
	
	@Override
	public DtoCollection<Credential> findAll() {
		return new DtoCollection<>(Collections.unmodifiableList(this.rep.findAll()));
	}
	
	@Override
	public Credential findById(final Integer credentialId) {
		return this.rep.findById(credentialId).orElseThrow(() -> new ObjectNotFoundException("###### NO Credential object FOUND! ######"));
	}
	
	@Override
	public Credential save(final Credential credential) {
		// credential.setPassword(this.encoder.encode(credential.getPassword()));
		return this.rep.save(credential);
	}
	
	@Override
	public Credential update(final Credential credential) {
		credential.setPassword(this.encoder.encode(credential.getPassword()));
		return this.rep.save(credential);
	}
	
	@Override
	public void deleteById(final Integer credentialId) {
		this.rep.delete(this.findById(credentialId));
	}
	
	/**
	 * @param username
	 * @return a userCredential instance
	 */
	@Override
	public Credential findByUsername(final String username) {
		return this.rep.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("###### NO Credential object FOUND with username: " + username + " ! ######"));
	}
	
	@Override
	public void deleteByUsername(final String username) {
		this.rep.delete(this.findByUsername(username));
	}
	
	
	
}










