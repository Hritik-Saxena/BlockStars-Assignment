package com.blockstars.blockstarsassignment.dto;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class UserDto {
    private String fullName;
    private String email;
    private String password;
    private BigDecimal totalSales;
    private String createdby;
    private String updatedby;

    // Getters and setters
}
