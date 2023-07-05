package com.rycoding.ecommerce.entities;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Phone {
    private String type;
    private String areaCode;
    private String number;
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Phone)) {
            return false;
        }
        Phone phone = (Phone) o;
        return getType().equals(phone.getType()) && getAreaCode().equals(phone.getAreaCode())
                && getNumber().equals(phone.getNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getType(), getAreaCode(), getNumber());
    }
}
