package ai.sxr.shoppingla.geoLocation.repository;

import ai.sxr.shoppingla.geoLocation.model.Division;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DivisionRepository extends JpaRepository<Division, Long> {
}
