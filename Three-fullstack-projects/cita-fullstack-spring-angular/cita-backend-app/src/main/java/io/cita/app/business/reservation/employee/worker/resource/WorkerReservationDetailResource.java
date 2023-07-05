package io.cita.app.business.reservation.employee.worker.resource;

import io.cita.app.business.reservation.employee.worker.service.WorkerReservationDetailService;
import io.cita.app.model.dto.response.ReservationDetailResponse;
import io.cita.app.model.dto.response.api.ApiResponse;
import io.cita.app.util.UserRequestExtractorUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

@RestController
@RequestMapping("${app.api-version}" + "/employees/workers/reservations/details")
@Slf4j
@RequiredArgsConstructor
public class WorkerReservationDetailResource {
	
	@Qualifier("workerRequestExtractorUtil")
	private final UserRequestExtractorUtil userRequestExtractorUtil;
	private final WorkerReservationDetailService workerReservationDetailService;
	
	@GetMapping("/{reservationId}")
	public ResponseEntity<ApiResponse<ReservationDetailResponse>> fetchReservationDetails(final WebRequest request,
                                                                                          @PathVariable final String reservationId) {
		log.info("** Fetch worker reservation details.. *");
		this.userRequestExtractorUtil.extractUsername(request);
		return ResponseEntity.ok(new ApiResponse<>(1, HttpStatus.OK, true, 
				this.workerReservationDetailService.fetchReservationDetails(Integer.parseInt(reservationId))));
	}
	
}




