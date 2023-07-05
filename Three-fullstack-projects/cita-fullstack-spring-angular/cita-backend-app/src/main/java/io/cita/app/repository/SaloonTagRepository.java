package io.cita.app.repository;

import io.cita.app.model.domain.entity.SaloonTag;
import io.cita.app.model.domain.id.SaloonTagId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SaloonTagRepository extends JpaRepository<SaloonTag, SaloonTagId> {
	
	List<SaloonTag> findAllBySaloonId(final Integer saloonId);
	
}



