package io.cita.app.business.reservation.employee.manager.service.impl;

import io.cita.app.business.reservation.ReservationCommonService;
import io.cita.app.business.reservation.employee.manager.model.ReservationAssignWorkerRequest;
import io.cita.app.business.reservation.employee.manager.model.ReservationSubWorkerResponse;
import io.cita.app.business.reservation.employee.manager.service.ManagerReservationDetailService;
import io.cita.app.mapper.OrderedDetailMapper;
import io.cita.app.mapper.ReservationMapper;
import io.cita.app.mapper.TaskMapper;
import io.cita.app.model.domain.ReservationStatus;
import io.cita.app.model.domain.entity.Task;
import io.cita.app.model.dto.TaskDto;
import io.cita.app.model.dto.response.ReservationBeginEndTask;
import io.cita.app.model.dto.response.ReservationDetailResponse;
import io.cita.app.repository.OrderedDetailRepository;
import io.cita.app.repository.ReservationRepository;
import io.cita.app.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import io.cita.app.exception.wrapper.ReservationNotFoundException;

import java.util.Comparator;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class ManagerReservationDetailServiceImpl implements ManagerReservationDetailService {
	
	private final ReservationRepository reservationRepository;
	private final ReservationCommonService reservationCommonService;
	private final OrderedDetailRepository orderedDetailRepository;
	private final TaskRepository taskRepository;
	
	@Override
	public ReservationDetailResponse fetchReservationDetails(final Integer reservationId) {
		log.info("** Fetch reservation details by reservationId by manager.. *");
		final var reservationDto = this.reservationRepository
				.findById(reservationId)
				.map(ReservationMapper::toDto)
				.orElseThrow(ReservationNotFoundException::new);
		final var orderedDetailDtoList = this.orderedDetailRepository
				.findAllByReservationId(reservationDto.getId()).stream()
					.map(OrderedDetailMapper::toDto)
					.toList();
		return ReservationDetailResponse.builder()
				.reservationDto(reservationDto)
				.orderedDetailDtos(new PageImpl<>(orderedDetailDtoList))
				.build();
	}
	
	@Override
	public ReservationBeginEndTask fetchBeginEndTask(final Integer reservationId) {
		log.info("** Fetch begin end task by manager.. *");
		
		final var tasks = this.taskRepository.findAllByReservationId(reservationId);
		
		final var firstTaskBegin = tasks.stream()
				.filter(t -> t.getStartDate() != null)
				// .filter(t -> t.getReservationDto().getStatus().equals(ReservationStatus.IN_PROGRESS))
				.min(Comparator.comparing(Task::getStartDate))
				.map(TaskMapper::toDto)
				.orElseGet(TaskDto::new);
		final var lastTaskEnd = tasks.stream()
				.filter(t -> t.getEndDate() != null)
				.filter(t -> t.getReservation().getStatus().equals(ReservationStatus.COMPLETED))
				.max(Comparator.comparing(Task::getEndDate))
				.map(TaskMapper::toDto)
				.orElseGet(TaskDto::new);
		
		return new ReservationBeginEndTask(firstTaskBegin, lastTaskEnd);
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
	
}



