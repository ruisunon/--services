package com.microservices.user.Requests;

public record CreateUserRequest(String full_name, String username, String password, String address) {
}
