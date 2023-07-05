package com.rycoding.ecommerce.entities;

import com.rycoding.ecommerce.entities.Phone;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Data
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "employee_phone", joinColumns = @JoinColumn(name = "employee_id"))
    private List<Phone> phones = new ArrayList<Phone>();
    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
