package tn.esb.lmad.flightPlannerAPI.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tn.esb.lmad.flightPlannerAPI.Domains.Flight;

import java.time.LocalDateTime;

@Repository
public interface FlightRepository extends JpaRepository<Flight,Integer> {
    //Custom Query written in JPQL (Java Persistence Query Language)
    @Query("SELECT f FROM Flight f WHERE f.departureDateTime = ?1")
    Flight findByFlightDepartureDateTime(LocalDateTime flightDepartureDateTime);

}
