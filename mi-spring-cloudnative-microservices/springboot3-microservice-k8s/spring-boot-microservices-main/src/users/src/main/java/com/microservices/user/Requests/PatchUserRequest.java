package com.microservices.user.Requests;

public record PatchUserRequest(Integer id, String full_name, String username, String address){
}
