package tn.esb.lmad.flightPlannerAPI.Domains;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString(exclude = {"logo"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@RequiredArgsConstructor //generates a constructor with required fields (final fields and @NonNull fields)
//@AllArgsConstructor // Because the id is auto generated, we don't need this annotation
@Table(name = "airlines")
@Entity
//in SQL the annotation is translated into
//CREATE TABLE airlines (code INTEGER PRIMARY KEY AUTO_INCREMENT, name VARCHAR(70) NOT NULL UNIQUE);

public class Airline {
    //primary key
    @Id
    //auto increment starts from 1 and increments by 1
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer code;
    @NonNull // JPA annotation to specify that a property is not nullable
    //the column is required and must be unique
    //@Column annotation to specify the column name, length ...
    @Column(unique = true,length = 70,name="airline_name")
    @EqualsAndHashCode.Include
    private String name;
    @NonNull
    private String contactInformations;
    private String headQuarters;
    @Lob // JPA annotation to specify that a property will be
    // persisted as a large object to a database-supported
    // large object type
    private byte[] logo;
    private int fleetSize;

    public Airline() {
    }

    public Airline(@NonNull String name, @NonNull String contactInformations, String headQuarters, byte[] logo, int fleetSize) {
        this.name = name;
        this.contactInformations = contactInformations;
        this.headQuarters = headQuarters;
        this.logo = logo;
        this.fleetSize = fleetSize;
    }
    //specify the relationship between Airline and aircraft (1 to many)
    @OneToMany(mappedBy = "airline")
    private Set<Aircraft> aircrafts=new HashSet<Aircraft>();
}
