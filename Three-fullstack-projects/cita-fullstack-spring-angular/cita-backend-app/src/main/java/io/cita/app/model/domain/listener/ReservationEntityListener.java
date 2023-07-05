package io.cita.app.model.domain.listener;

import io.cita.app.model.domain.ReservationStatus;
import io.cita.app.model.domain.entity.Reservation;
import jakarta.persistence.PrePersist;

import java.util.UUID;

public class ReservationEntityListener {
	
	@PrePersist
	public void preCreate(final Reservation reservation) {
		reservation.setCode((reservation.getCode() == null || reservation.getCode().isBlank()) ? 
				UUID.randomUUID().toString() : reservation.getCode());
		reservation.setStatus(ReservationStatus.NOT_STARTED);
	}
	
}



