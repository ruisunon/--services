package com.rytest.auth;

public record AuthenticationRequest(
        String username,
        String password
) {
}
