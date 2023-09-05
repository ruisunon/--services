package ai.sxr.shoppingla.geoLocation.repository;

import ai.sxr.shoppingla.geoLocation.model.District;
import ai.sxr.shoppingla.geoLocation.model.Upazila;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UpazilaRepository extends JpaRepository<Upazila, Long> {
    public List<Upazila> findAllByDistrictIdOrderById(District districtId);
}
