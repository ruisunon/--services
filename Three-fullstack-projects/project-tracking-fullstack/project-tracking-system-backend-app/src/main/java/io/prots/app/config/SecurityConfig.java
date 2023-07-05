package io.prots.app.config;

import com.nimbusds.jose.jwk.RSAKey;
import io.prots.app.repository.UserRepository;
import io.prots.app.security.RSAKeyProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true) // Enable @PreAuthorize method-level security
public class SecurityConfig {
	private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);
	private final RSAKey rsaKey;

	public SecurityConfig(RSAKeyProvider RSAKeyProvider) {
		this.rsaKey = RSAKeyProvider.getRsaKey();
	}

	@Bean
	public AuthenticationManager authManager(UserDetailsService userDetailsService) {
		var authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		return new ProviderManager(authProvider);
	}

	@Bean
	public UserDetailsService userDetailsService(UserRepository userRepository) {
		return username -> userRepository
				.findByUsername(username)
				.orElseThrow(
						() -> new UsernameNotFoundException(username)
				);
	}
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.anyRequest()
				.authenticated();
		return http.build();
	}
	@Bean
	public CorsConfigurationSource corsConfigurationSource(Environment env) {

		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);

		String allowedOrigins = env.getProperty("PETCLINIC_ALLOWED_ORIGINS", "http://localhost:3000");

		Arrays.stream(allowedOrigins.split(","))
				.forEach(origin -> {
					log.info("Allowing Cors for host '{}'", origin);
					config.addAllowedOrigin(origin);
				});

		config.addAllowedHeader("*");
		config.addAllowedMethod("*");

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		return source;
	}
}









