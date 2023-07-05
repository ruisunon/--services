package io.cita.app.business.reservation.employee.worker.service.impl;

import io.cita.app.business.reservation.employee.worker.service.WorkerReservationDetailService;
import io.cita.app.mapper.OrderedDetailMapper;
import io.cita.app.mapper.ReservationMapper;
import io.cita.app.mapper.TaskMapper;
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

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class WorkerReservationDetailServiceImpl implements WorkerReservationDetailService {
	
	private final TaskRepository taskRepository;
	private final ReservationRepository reservationRepository;
	private final OrderedDetailRepository orderedDetailRepository;
	
	@Override
	public ReservationDetailResponse fetchReservationDetails(final Integer reservationId) {
		log.info("** Fetch reservation details by reservationId by worker.. *");
		final var reservationDto = this.reservationRepository.findById(reservationId)
				.map(ReservationMapper::toDto)
				.orElseThrow(ReservationNotFoundException::new);
		return ReservationDetailResponse.builder()
				.reservationDto(reservationDto)
				.orderedDetailDtos(new PageImpl<>(this.orderedDetailRepository
						.findAllByReservationId(reservationDto.getId()).stream()
						.map(OrderedDetailMapper::toDto)
						.toList()))
				.taskDtos(new PageImpl<>(this.taskRepository
						.findAllByReservationId(reservationDto.getId()).stream()
						.map(TaskMapper::toDto)
						.toList()))
				.build();
	}
	
}




