package ai.sxr.shoppingla.geoLocation.repository;

import ai.sxr.shoppingla.geoLocation.model.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DistrictRepository extends JpaRepository<District, Long> {
    public List<District> findAllByDivisionIdOrderById(Long divisionId);
}
