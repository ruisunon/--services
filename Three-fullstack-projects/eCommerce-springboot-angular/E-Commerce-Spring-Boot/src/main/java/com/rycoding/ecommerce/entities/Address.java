package com.rycoding.ecommerce.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String building;
    @Column(name="STREET")
    private String street;
    private String state;
    private String country;
    @Column(name="ZIP_CODE")
    private String zipcode;
    @OneToOne
    private User user;
}
