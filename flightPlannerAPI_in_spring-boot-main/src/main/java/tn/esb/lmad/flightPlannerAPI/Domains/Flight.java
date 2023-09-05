package tn.esb.lmad.flightPlannerAPI.Domains;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;
import tn.esb.lmad.flightPlannerAPI.Enumerations.FlightStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor //generates a constructor with required fields (final fields and @NonNull fields)
//@AllArgsConstructor // Because the id is auto generated, we don't need this annotation
@Table(name = "airlines")
@Entity
public class Flight {
    //primary key
    @Id
    //auto increment starts from 1 and increments by 1
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer code;
    private String name;
    private int distance;
    //JsonFormat is used to format the date and time in the JSON request and response
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime departureDateTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime arrivalDateTime;
    @Min(0) //the price must be positive
    @Max(1000)//the price must be less than 1000
    private BigDecimal price;
    private float duration;
    private boolean isDirect;
    @Enumerated(EnumType.STRING)
    @Column(length = 10,columnDefinition = "varchar(10) default 'SCHEDULED'")
    private FlightStatus status;
    //Specify the relationship between Flight and Airport (* to *)
    @ManyToMany(mappedBy = "flights")
    Set<Airport> airports=new HashSet<>();

}
