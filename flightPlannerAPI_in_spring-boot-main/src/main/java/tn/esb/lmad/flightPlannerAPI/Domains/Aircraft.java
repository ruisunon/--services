package tn.esb.lmad.flightPlannerAPI.Domains;

import jakarta.persistence.*;
import lombok.*;
import tn.esb.lmad.flightPlannerAPI.Enumerations.AircraftModel;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
   // JPA annotation to make this object ready for storage in a JPA-based data store
//the database is a relational one
@Entity
@Table(name = "aircrafts")
public class Aircraft {
    @Id // JPA annotation to specify the primary key of an entity
    @EqualsAndHashCode.Include // to include this field in the equality and hash code calculations
    private String code;
    @Enumerated(EnumType.STRING)
    private AircraftModel model;
    private int maxPassengerCapacity;
    private  int range;
    private BigDecimal fuelConsumption;

    //Specify the relationship between Aircraft and Airline (many to 1)
    @ManyToOne
    @JoinColumn(name="airline_code")
    private Airline airline;

}
