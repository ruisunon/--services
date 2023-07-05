package io.cita.app.business.reservation.employee.manager.service.impl;

import io.cita.app.business.reservation.ReservationCommonService;
import io.cita.app.business.reservation.employee.manager.model.ManagerReservationResponse;
import io.cita.app.business.reservation.employee.manager.model.ReservationAssignWorkerRequest;
import io.cita.app.business.reservation.employee.manager.model.ReservationSubWorkerResponse;
import io.cita.app.business.reservation.employee.manager.service.ManagerReservationService;
import io.cita.app.mapper.EmployeeMapper;
import io.cita.app.mapper.ReservationMapper;
import io.cita.app.model.dto.EmployeeDto;
import io.cita.app.model.dto.ReservationDto;
import io.cita.app.model.dto.SaloonDto;
import io.cita.app.model.dto.request.ClientPageRequest;
import io.cita.app.repository.EmployeeRepository;
import io.cita.app.repository.ReservationRepository;
import io.cita.app.util.ClientPageRequestUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import io.cita.app.exception.wrapper.EmployeeNotFoundException;

import java.util.Comparator;
import java.util.Objects;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class ManagerReservationServiceImpl implements ManagerReservationService {
	
	private final EmployeeRepository employeeRepository;
	private final ReservationRepository reservationRepository;
	private final ReservationCommonService reservationCommonService;
	
	/**
	 * Bug: When a new user is registered, this API produce NullPointerException <br>
	 * cause this new user is not assigned to any saloon yet. (due to nullable saloonId)
	 * @param username
	 * @return ManagerReservationResponse
	 */
	@Override
	public ManagerReservationResponse fetchAllReservations(final String username) {
		log.info("** Fetch all reservations by manager.. *");
		final var managerDto = this.retrieveManagerByUsername(username);
		final var foundReservations = this.reservationRepository.findAllBySaloonId(
						Objects.requireNonNullElse(managerDto.getSaloonDto(), new SaloonDto()).getId()).stream()
					.map(ReservationMapper::toDto)
					.sorted(Comparator
							.comparing(ReservationDto::getStartDate)
							.reversed())
					.toList();
		return new ManagerReservationResponse(managerDto, new PageImpl<>(foundReservations));
	}
	
	/**
	 * Bug: When a new user is registered, this API produce NullPointerException <br>
	 * cause this new user is not assigned to any saloon yet. (due to nullable saloonId)
	 * @param username
	 * @param clientPageRequest
	 * @return ManagerReservationResponse
	 */
	@Override
	public ManagerReservationResponse fetchAllReservations(final String username, final ClientPageRequest clientPageRequest) {
		log.info("** Fetch paged reservations by manager.. *");
		final var managerDto = this.retrieveManagerByUsername(username);
		return new ManagerReservationResponse(
				managerDto,
				this.reservationRepository.findAllBySaloonId(
								Objects.requireNonNullElse(managerDto.getSaloonDto(), new SaloonDto()).getId(),
								ClientPageRequestUtils.from(clientPageRequest))
						.map(ReservationMapper::toDto));
	}
	
	@Transactional
	@Override
	public ReservationDto cancelReservation(final Integer reservationId) {
		return this.reservationCommonService.cancelReservation(reservationId);
	}
	
	@Override
	public ManagerReservationResponse searchAllBySaloonIdLikeKey(final String username, final String key) {
		log.info("** Search all reservations by saloonId like key by manager.. *");
		final var managerDto = this.retrieveManagerByUsername(username);
		final var foundReservations = this.reservationRepository
				.searchAllBySaloonIdLikeKey(
						managerDto.getSaloonDto().getId(), key.strip().toLowerCase()).stream()
					.map(ReservationMapper::toDto)
					.sorted(Comparator
							.comparing(ReservationDto::getStartDate)
							.reversed())
					.toList();
		return new ManagerReservationResponse(managerDto, new PageImpl<>(foundReservations));
	}
	
	@Override
	public ReservationSubWorkerResponse fetchAllUnassignedSubWorkers(final String username, final Integer reservationId) {
		return this.reservationCommonService.fetchAllUnassignedSubWorkers(username, reservationId);
	}
	
	@Transactional
	@Override
	public ReservationSubWorkerResponse assignReservationWorkers(final String username, 
			final ReservationAssignWorkerRequest reservationAssignWorkerRequest) {
		return this.reservationCommonService.assignReservationWorkers(username, reservationAssignWorkerRequest);
	}
	
	private EmployeeDto retrieveManagerByUsername(final String username) {
		return this.employeeRepository
				.findByCredentialUsernameIgnoringCase(username)
				.map(EmployeeMapper::toDto)
				.orElseThrow(() -> new EmployeeNotFoundException(
						"Employee with username: %s not found".formatted(username)));
	}
	
}



