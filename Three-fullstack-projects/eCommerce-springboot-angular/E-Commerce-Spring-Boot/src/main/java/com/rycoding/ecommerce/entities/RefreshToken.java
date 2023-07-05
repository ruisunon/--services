package com.rycoding.ecommerce.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "refresh-token")
public class RefreshToken {
    @Id
    @GeneratedValue
    public Long id;
    @Enumerated(EnumType.STRING)
    public RefreshToken.TokenType tokenType = RefreshToken.TokenType.BEARER;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private Instant expiryDate;

    public static enum TokenType {
        BEARER
    }
}
