package io.cita.app.security;

import io.cita.app.model.dto.CredentialDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

public record CustomUserDetails(CredentialDto credentialDto) implements UserDetails {
	
	@Override
	public String getUsername() {
		return this.credentialDto.getUsername();
	}
	
	@Override
	public String getPassword() {
		return this.credentialDto.getPassword();
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Set.of(new SimpleGrantedAuthority(this.credentialDto
				.getUserRoleBasedAuthority().getRole()));
	}
	
	@Override
	public boolean isEnabled() {
		return this.credentialDto.getIsEnabled();
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return this.credentialDto.getIsAccountNonExpired();
	}
	
	@Override
	public boolean isAccountNonLocked() {
		return this.credentialDto.getIsAccountNonLocked();
	}
	
	@Override
	public boolean isCredentialsNonExpired() {
		return this.credentialDto.getIsCredentialsNonExpired();
	}
	
}



