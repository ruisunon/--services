package io.cita.app.model.domain.listener;

import io.cita.app.model.domain.entity.Task;
import jakarta.persistence.PrePersist;

import java.time.LocalDateTime;

public class TaskEntityListener {
	
	@PrePersist
	public void preCreate(final Task task) {
		task.setTaskDate(LocalDateTime.now());
	}
	
}



