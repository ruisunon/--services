package io.cita.app.business.reservation.customer.service.impl;

import io.cita.app.business.reservation.customer.service.CustomerReservationDetailService;
import io.cita.app.mapper.OrderedDetailMapper;
import io.cita.app.mapper.ReservationMapper;
import io.cita.app.mapper.TaskMapper;
import io.cita.app.model.dto.ReservationDto;
import io.cita.app.model.dto.request.ReservationDetailRequest;
import io.cita.app.model.dto.response.ReservationDetailResponse;
import io.cita.app.util.StringWrapperUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import io.cita.app.exception.wrapper.ReservationNotFoundException;
import io.cita.app.repository.OrderedDetailRepository;
import io.cita.app.repository.ReservationRepository;
import io.cita.app.repository.TaskRepository;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class CustomerReservationDetailServiceImpl implements CustomerReservationDetailService {
	
	private final ReservationRepository reservationRepository;
	private final OrderedDetailRepository orderedDetailRepository;
	private final TaskRepository taskRepository;
	
	@Override
	public ReservationDetailResponse fetchReservationDetails(final Integer reservationId) {
		log.info("** Fetch reservation details by reservationId by customer.. *");
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
	
	@Override
	public ReservationDetailResponse fetchReservationDetails(final String reservationIdentifier) {
		log.info("** Fetch reservation details by reservationIdentifier by customer.. *");
		final var reservationDto = this.reservationRepository
				.findByIdentifier(reservationIdentifier.strip())
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
	
	@Transactional
	@Override
	public ReservationDto updateReservationDetails(final ReservationDetailRequest reservationDetailRequest) {
		log.info("** Update reservation details by customer.. *");
		final var reservation = this.reservationRepository
				.findById(reservationDetailRequest.reservationId())
				.orElseThrow(ReservationNotFoundException::new);
		reservation.setDescription(StringWrapperUtils
				.trimIfBlank(reservationDetailRequest.description()));
		
		return ReservationMapper.toDto(this.reservationRepository.save(reservation));
	}
	
}




