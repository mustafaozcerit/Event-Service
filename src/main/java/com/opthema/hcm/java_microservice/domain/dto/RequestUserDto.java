package com.opthema.hcm.java_microservice.domain.dto;

public class RequestUserDto {
    private String username;
    private String password;

    public RequestUserDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() { return password; }
}
