package tn.esb.lmad.flightPlannerAPI.Domains;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor //generates a constructor with required fields (final fields and @NonNull fields)
//@AllArgsConstructor // Because the id is auto generated, we don't need this annotation
@Table(name = "employees")
@Entity
public class Employee {
    //primary key
    @Id
    //auto increment starts from 1 and increments by 1
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String jobTitle;
    private BigDecimal salary;

    //Specify the relationship between Employee and EmployeeContact (1 to 1)
    //mappedBy = "employee" means that the relationship is owned by the other side (EmployeeContact)
    //the two sides of the relationship comes from the same relationship
    @OneToOne(mappedBy = "employee")
    @JoinColumn(name = "contact_id")
    private EmployeeContact employeeContact;
}
