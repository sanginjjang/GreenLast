package com.example.greenlast.dto;

/**
 * Created on 2025-01-22 by 한상인
 */
import lombok.*;
@Data
public class UserDTO {
    private String userId;
    private String password;
    private String name;
    private String phoneNumber;
    private String email;
    private int birth;
    private String gender;
    private String createdAt;
    private String role;
    private String user_image;
}

