package ai.sxr.shoppingla.auth.service;

import ai.sxr.shoppingla.auth.repository.TokenRepository;
import ai.sxr.shoppingla.auth.repository.UserRepository;
import ai.sxr.shoppingla.utils.GenericResponse;
import ai.sxr.shoppingla.utils.ResponseCode;
import ai.sxr.shoppingla.auth.dto.LoginRequest;
import ai.sxr.shoppingla.auth.dto.LoginResponse;
import ai.sxr.shoppingla.auth.dto.RegisterRequest;
import ai.sxr.shoppingla.auth.model.Token;
import ai.sxr.shoppingla.auth.model.TokenType;
import ai.sxr.shoppingla.auth.model.User;
import ai.sxr.shoppingla.auth.dto.UserDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public GenericResponse register(RegisterRequest request) {
        try {
            var user = User.builder()
                    .firstname(request.getFirstname())
                    .lastname(request.getLastname())
                    .email(request.getEmail())
                    .username(request.getUsername())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .registerDate(new Date())
                    .active(request.isActive())
                    .build();
            var savedUser = userRepository.save(user);
//            var jwtToken = jwtService.generateToken(user);
//            var refreshToken = jwtService.generateRefreshToken(user);
//    saveUserToken(savedUser, jwtToken);
//            saveUserToken(savedUser, refreshToken);
            return new GenericResponse(ResponseCode.OK.getCode(), "Successfully Registered");
        } catch (Exception ex) {
            return new GenericResponse(ResponseCode.SERVICE_ERROR.getCode(), "Service Error");
        }
    }

    public LoginResponse authenticate(LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
            var user = userRepository.findByUsername(request.getUsername())
                    .orElseThrow();
            var jwtToken = jwtService.generateToken(user);
            var refreshToken = jwtService.generateRefreshToken(user);
//            revokeAllUserTokens(user);
            saveUserToken(user, refreshToken);
            return LoginResponse.builder()
                    .accessToken(jwtToken)
                    .refreshToken(refreshToken)
                    .user(new UserDto(user.getId(), user.getFirstname(), user.getLastname(),
                            user.getEmail(), user.getUsername(), ""))
                    .build();
        } catch (Exception ex) {
            LoginResponse response = new LoginResponse();
            response.setStatus(ResponseCode.AUTHENTICATION_ERROR.getCode());
            response.setRemarks("Authentication Error");
            return response;
        }
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .refreshToken(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    public User findUserByUserName(String userName) {
        return userRepository.findByUsername(userName).get();
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);

        var storedToken = tokenRepository.findByRefreshToken(refreshToken)
                .orElse(null);

        if (storedToken != null) {
            User user = storedToken.getUser();
            var accessToken = jwtService.generateToken(user);
            saveUserToken(user, refreshToken);
            var authResponse = LoginResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();
            new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
        }
    }
}
