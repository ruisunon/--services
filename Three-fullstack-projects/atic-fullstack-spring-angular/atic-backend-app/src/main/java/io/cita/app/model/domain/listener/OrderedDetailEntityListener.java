package io.cita.app.model.domain.listener;

import io.cita.app.model.domain.entity.OrderedDetail;
import jakarta.persistence.PrePersist;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderedDetailEntityListener {
	
	@PrePersist
	public void preCreate(final OrderedDetail orderedDetail) {
		orderedDetail.setOrderedDate(LocalDateTime.now());
	}
	
}



