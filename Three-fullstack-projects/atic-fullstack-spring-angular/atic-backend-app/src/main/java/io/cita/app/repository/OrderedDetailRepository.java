package io.cita.app.repository;

import io.cita.app.model.domain.entity.OrderedDetail;
import io.cita.app.model.domain.id.OrderedDetailId;
import io.cita.app.model.dto.request.OrderedDetailRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderedDetailRepository extends JpaRepository<OrderedDetail, OrderedDetailId> {
	
	Optional<OrderedDetail> findByIdentifier(final String identifier);
	List<OrderedDetail> findAllByReservationId(final Integer reservationId);
	Page<OrderedDetail> findAllByReservationId(final Integer reservationId, final Pageable pageable);
	Page<OrderedDetail> findAllByServiceDetailId(final Integer serviceDetailId, final Pageable pageable);
	
	@Modifying
	@Query(name = "int.saveOrderedDetail", nativeQuery = true)
	int saveOrderedDetail(@Param("orderedDetail") final OrderedDetailRequest orderedDetail);
	
}



