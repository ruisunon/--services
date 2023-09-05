package tn.esb.lmad.flightPlannerAPI.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esb.lmad.flightPlannerAPI.Domains.Airline;

@Repository
public interface AirlineRepository extends JpaRepository<Airline,Integer> {
}
