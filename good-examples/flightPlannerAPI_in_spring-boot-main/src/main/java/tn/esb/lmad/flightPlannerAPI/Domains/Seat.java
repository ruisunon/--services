package tn.esb.lmad.flightPlannerAPI.Domains;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor //generates a constructor with required fields (final fields and @NonNull fields)
//@AllArgsConstructor // Because the id is auto generated, we don't need this annotation
@Table(name = "seats")
@Entity
public class Seat {
    //primary key
    @Id
    //auto increment starts from 1 and increments by 1
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "seat_number")
    @NonNull
    private int seatNumber;
    @Column(name = "seat_class",columnDefinition = "varchar(255) default 'Economy'")
    @NonNull
    private String seatClass;
    @Column(name = "seat_price",precision = 7,scale = 2)
    //@Digits(integer = 7,fraction = 2)
    @NonNull
    private BigDecimal seatPrice;
    @Size(min = 25,max = 50)
    private BigDecimal seatPitch;
    private boolean isAvailable;
    @Min(0)
    @Max(45)
    private BigDecimal recline;
    private String entertainmentOptions;


    public Seat() {

    }

    public Seat(@NonNull int seatNumber, @NonNull String seatClass, @NonNull BigDecimal seatPrice, BigDecimal seatPitch, boolean isAvailable, BigDecimal recline, String entertainmentOptions) {
        this.seatNumber = seatNumber;
        this.seatClass = seatClass;
        this.seatPrice = seatPrice;
        this.seatPitch = seatPitch;
        this.isAvailable = isAvailable;
        this.recline = recline;
        this.entertainmentOptions = entertainmentOptions;
    }
}
