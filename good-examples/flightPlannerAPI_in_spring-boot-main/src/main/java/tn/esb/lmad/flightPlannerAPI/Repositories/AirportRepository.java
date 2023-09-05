package tn.esb.lmad.flightPlannerAPI.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esb.lmad.flightPlannerAPI.Domains.Airport;
@Repository
public interface AirportRepository extends JpaRepository<Airport,Integer> {
}
