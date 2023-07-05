package com.rycoding.ecommerce.entities;

import com.rycoding.ecommerce.dto.UserDTO;
import com.rycoding.ecommerce.enums.ContactType;
import com.rycoding.ecommerce.enums.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private byte[] img;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_phone", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "phone_number")
    private List<Phone> phones = new ArrayList<Phone>();


    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    public String getUsername() {
        return this.email;
    }
    public UserDTO mapUsertoUserDto() {

        return UserDTO.builder().id(id).email(email).firstname(firstname).lastname(lastname).userRole(userRole).img(img).build();
    }

    @OneToMany(mappedBy = "user")
    private List<RefreshToken> tokens;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userRole.getAuthorities();
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
