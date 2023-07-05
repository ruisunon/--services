package io.cita.app.business.reservation.employee.manager.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.cita.app.model.dto.EmployeeDto;
import io.cita.app.model.dto.TaskDto;
import lombok.Builder;
import org.springframework.data.domain.Page;

import java.io.Serializable;

@Builder
public record ManagerWorkerAssignmentResponse(
		
		@JsonProperty("manager")
		@JsonInclude(Include.NON_NULL) EmployeeDto managerDto,
		
		@JsonProperty("tasks")
		@JsonInclude(Include.NON_NULL)
		Page<TaskDto> tasksDtos) implements Serializable {}


