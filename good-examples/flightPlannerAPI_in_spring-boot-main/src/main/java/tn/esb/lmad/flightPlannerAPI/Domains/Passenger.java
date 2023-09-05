package tn.esb.lmad.flightPlannerAPI.Domains;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor //generates a constructor with required fields (final fields and @NonNull fields)
//@AllArgsConstructor // Because the id is auto generated, we don't need this annotation
@Table(name = "passenger")
@Entity
public class Passenger{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String familyName;
    @Email //this annotation will check if the email is valid
    //the string email is introduced like '[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}'
    private String email;
    @Column(length = 18)
    private String phoneNumber;
    private String Nationality;
    private String passportNumber;
    //Specify the relationship between Booking and Passenger (1 to *)
    @OneToMany(mappedBy = "passenger")
    private List<Booking> bookings;
}
