package tn.esb.lmad.flightPlannerAPI.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esb.lmad.flightPlannerAPI.Domains.Passenger;
@Repository
public interface PassengerRepository extends JpaRepository<Passenger,Long> {
}
