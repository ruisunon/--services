package ai.sxr.shoppingla.geoLocation.model;

import ai.sxr.shoppingla.auth.model.GenericModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "division")
public class Division extends GenericModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "division_generator")
    @SequenceGenerator(name="division_generator", sequenceName = "division_seq", initialValue=1)
    private Long id;

    @Column(name = "name", unique = true)
    private String name;
}
