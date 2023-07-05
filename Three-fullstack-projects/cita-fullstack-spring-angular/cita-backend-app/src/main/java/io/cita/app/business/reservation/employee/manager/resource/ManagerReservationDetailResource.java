package io.cita.app.business.reservation.employee.manager.resource;

import io.cita.app.business.reservation.employee.manager.model.ReservationAssignWorkerRequest;
import io.cita.app.business.reservation.employee.manager.model.ReservationSubWorkerResponse;
import io.cita.app.business.reservation.employee.manager.service.ManagerReservationDetailService;
import io.cita.app.model.dto.response.ReservationBeginEndTask;
import io.cita.app.model.dto.response.ReservationDetailResponse;
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

@RestController
@RequestMapping("${app.api-version}" + "/employees/managers/reservations/details")
@Slf4j
@RequiredArgsConstructor
public class ManagerReservationDetailResource {
	
	@Qualifier("managerRequestExtractorUtil")
	private final UserRequestExtractorUtil userRequestExtractorUtil;
	private final ManagerReservationDetailService managerReservationDetailService;
	
	@GetMapping("/{reservationId}")
	public ResponseEntity<ApiResponse<ReservationDetailResponse>> fetchReservationDetails(
			final WebRequest request, @PathVariable final String reservationId) {
		log.info("** Fetch manager reservation details.. *");
		this.userRequestExtractorUtil.extractUsername(request);
		return ResponseEntity.ok(new ApiResponse<>(1, HttpStatus.OK, true, 
				this.managerReservationDetailService.fetchReservationDetails(Integer.parseInt(reservationId))));
	}
	
	@GetMapping("/{reservationId}/tasks/info/beginEnd")
	public ResponseEntity<ApiResponse<ReservationBeginEndTask>> fetchBeginEndTask(
			final WebRequest webRequest, @PathVariable final String reservationId) {
		log.info("** Fetch manager begin and end task.. *");
		this.userRequestExtractorUtil.extractUsername(webRequest);
		return ResponseEntity.ok(new ApiResponse<>(1, HttpStatus.OK, true, 
				this.managerReservationDetailService.fetchBeginEndTask(Integer.parseInt(reservationId))));
	}
	
	@GetMapping("/{reservationId}/tasks/unassigned")
	public ResponseEntity<ApiResponse<ReservationSubWorkerResponse>> fetchAllUnassignedSubWorkers(
			final WebRequest webRequest, @PathVariable final String reservationId) {
		log.info("** Fetch all manager unassigned sub workers.. *");
		return ResponseEntity.ok(new ApiResponse<>(1, HttpStatus.OK, true,
				this.managerReservationDetailService.fetchAllUnassignedSubWorkers(
						this.userRequestExtractorUtil.extractUsername(webRequest), Integer.parseInt(reservationId))));
	}
	
	@PostMapping("/tasks/assign")
	public ResponseEntity<ApiResponse<ReservationSubWorkerResponse>> assignReservationWorkers(final WebRequest webRequest, 
			@RequestBody @Valid final ReservationAssignWorkerRequest reservationAssignWorkerRequest) {
		log.info("** Assign sub workers to a reservation by manager.. *");
		return ResponseEntity.ok(new ApiResponse<>(1, HttpStatus.OK, true,
				this.managerReservationDetailService.assignReservationWorkers(
						this.userRequestExtractorUtil.extractUsername(webRequest),
						reservationAssignWorkerRequest)));
	}
	
}




