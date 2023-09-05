package com.coderxs.models;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "product", schema = "public")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private long id;

    private String name;
    private String category;
    private double price;

    private String description;
    private LocalDate madeDate;

    public Product(String title, String description, LocalDate published) {
        this.name = title;
        this.description = description;
        this.madeDate = published;
    }

    @Override
    public String toString() {
        return "Tutorial [id=" + id + ", name=" + name + ", desc=" + description + ", published=" + madeDate + "]";
    }

}