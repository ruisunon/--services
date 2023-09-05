package ai.sxr.shoppingla.auth.controller;

import ai.sxr.shoppingla.utils.GenericResponse;
import ai.sxr.shoppingla.auth.dto.LoginRequest;
import ai.sxr.shoppingla.auth.dto.LoginResponse;
import ai.sxr.shoppingla.auth.dto.LogoutRequest;
import ai.sxr.shoppingla.auth.dto.RegisterRequest;
import ai.sxr.shoppingla.auth.service.AuthenticationService;
import ai.sxr.shoppingla.auth.service.LogoutService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthenticationController {

    private final AuthenticationService service;
    private final LogoutService logoutService;

    @PostMapping("/register")
    public GenericResponse register(
            @RequestBody RegisterRequest request
    ) {
        return service.register(request);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(
            @RequestBody LoginRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        service.refreshToken(request, response);
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request,
                       HttpServletResponse response, @RequestBody LogoutRequest logoutRequest) throws IOException {
        logoutService.logoutCustom(request, response, logoutRequest);
    }

}
