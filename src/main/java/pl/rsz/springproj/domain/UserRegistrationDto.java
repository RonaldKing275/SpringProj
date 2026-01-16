package pl.rsz.springproj.domain;

import lombok.Data;

@Data
public class UserRegistrationDto {
    private String username;
    private String password;
    private String confirmPassword;
}