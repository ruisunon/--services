package io.cita.app.business.profile.employee.manager.resource;

import io.cita.app.business.profile.employee.manager.model.ManagerProfileRequest;
import io.cita.app.business.profile.employee.manager.model.ManagerProfileResponse;
import io.cita.app.model.dto.EmployeeDto;
import io.cita.app.model.dto.response.api.ApiResponse;
import io.cita.app.util.UserRequestExtractorUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import io.cita.app.business.profile.employee.manager.service.ManagerProfileService;

@RestController
@RequestMapping("${app.api-version}" + "/employees/managers/profile")
@Slf4j
@RequiredArgsConstructor
public class ManagerProfileResource {
	
	@Qualifier("managerRequestExtractorUtil")
	private final UserRequestExtractorUtil userRequestExtractorUtil;
	private final ManagerProfileService managerProfileService;
	
	@GetMapping
	public ResponseEntity<ApiResponse<ManagerProfileResponse>> fetchProfile(final WebRequest webRequest) {
		log.info("** Fetch manager profile info.. *\n");
		return ResponseEntity.ok(new ApiResponse<>(1, HttpStatus.OK, true, 
				this.managerProfileService.fetchProfile(this.userRequestExtractorUtil.extractUsername(webRequest))));
	}
	
	@PutMapping
	public ResponseEntity<ApiResponse<EmployeeDto>> updateProfileInfo(final WebRequest webRequest,
                                                                      @RequestBody @Valid final ManagerProfileRequest managerProfileRequest) {
		log.info("** Update manager profile info.. *\n");
		this.userRequestExtractorUtil.extractUsername(webRequest);
		return ResponseEntity.ok(new ApiResponse<>(1, HttpStatus.OK, true, 
				this.managerProfileService.updateProfileInfo(managerProfileRequest)));
	}
	
}




