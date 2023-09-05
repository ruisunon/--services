package tn.esb.lmad.flightPlannerAPI.Domains;

import jakarta.persistence.*;
import lombok.*;
import tn.esb.lmad.flightPlannerAPI.Enumerations.PaymentMethodType;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor //generates a constructor with required fields (final fields and @NonNull fields)
//@AllArgsConstructor // Because the id is auto generated, we don't need this annotation
@Table(name = "bookings")
@Entity
public class Booking {
    //primary key
    @Id
    //auto increment starts from 1 and increments by 1
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer code;
    private LocalDate bookingDate;
    private BigDecimal price;
    @Enumerated(EnumType.STRING)
    @Column(length = 10,columnDefinition = "varchar(10) default 'CASH'")
    private PaymentMethodType paymentMethod;
    @Column(columnDefinition = "boolean default false")
    private Boolean isPaid;
    //Specify the relationship between Booking and Passenger (* to 1)
    @ManyToOne
    @JoinColumn(name = "passenger_id")
    private Passenger passenger;
}
