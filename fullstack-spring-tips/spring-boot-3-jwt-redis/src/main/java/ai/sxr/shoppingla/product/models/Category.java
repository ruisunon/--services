package ai.sxr.shoppingla.product.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@JsonIgnoreProperties(value = { "products" })
@Data
@AllArgsConstructor
@ToString
@Table(name = "categories")
@Entity
public class Category {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column(nullable = false, length = 50, unique = true)
    private String name;

    @CreationTimestamp
    protected LocalDate createdAt;

    @UpdateTimestamp
    protected LocalDate  updatedAt;

    @OneToMany(mappedBy = "category")
    @JsonManagedReference(value = "category-products")
    @ToString.Exclude
    private Set<Product> products;

    public Category() {
        products = new HashSet<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Category category = (Category) o;
        return id != null && Objects.equals(id, category.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}