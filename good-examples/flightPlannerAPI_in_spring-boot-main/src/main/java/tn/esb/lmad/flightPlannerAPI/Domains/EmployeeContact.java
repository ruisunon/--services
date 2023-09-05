package tn.esb.lmad.flightPlannerAPI.Domains;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor //generates a constructor with required fields (final fields and @NonNull fields)
//@AllArgsConstructor // Because the id is auto generated, we don't need this annotation
@Table(name = "employees_contacts")
@Entity
public class EmployeeContact {
    //primary key
    @Id
    //auto increment starts from 1 and increments by 1
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer code;
    private String phoneNumber;
    private String mailingAddress;
    private String email;

    //Specify the relationship between EmployeeContact and Employee (1 to 1)
    @OneToOne
    private Employee employee;
}
