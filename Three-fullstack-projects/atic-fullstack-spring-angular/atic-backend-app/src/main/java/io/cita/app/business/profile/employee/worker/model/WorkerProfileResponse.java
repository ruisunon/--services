package io.cita.app.business.profile.employee.worker.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.cita.app.model.dto.CredentialDto;
import io.cita.app.model.dto.EmployeeDto;
import io.cita.app.model.dto.TaskDto;
import lombok.Builder;
import org.springframework.data.domain.Page;

import java.io.Serializable;

@Builder
public record WorkerProfileResponse(
		
		@JsonProperty("worker") EmployeeDto workerDto,
		
		@JsonInclude(value = Include.NON_NULL)
		@JsonProperty("credential") CredentialDto credentialDto,
		
		@JsonInclude(value = Include.NON_NULL)
		@JsonProperty("tasks")
		Page<TaskDto> taskDtos) implements Serializable {}


