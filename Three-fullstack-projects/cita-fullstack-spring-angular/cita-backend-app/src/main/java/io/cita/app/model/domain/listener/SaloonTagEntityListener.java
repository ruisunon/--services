package io.cita.app.model.domain.listener;

import io.cita.app.model.domain.entity.SaloonTag;
import jakarta.persistence.PrePersist;

import java.time.LocalDateTime;

public class SaloonTagEntityListener {
	
	@PrePersist
	public void preCreate(final SaloonTag saloonTag) {
		saloonTag.setTaggedDate(LocalDateTime.now());
	}
	
}



