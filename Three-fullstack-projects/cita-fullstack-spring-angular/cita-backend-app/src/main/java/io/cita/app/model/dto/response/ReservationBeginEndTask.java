package io.cita.app.model.dto.response;

import io.cita.app.model.dto.TaskDto;
import lombok.Builder;

import java.io.Serializable;

@Builder
public record ReservationBeginEndTask(TaskDto taskBegin, TaskDto taskEnd) implements Serializable {}


