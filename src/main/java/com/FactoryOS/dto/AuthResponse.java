package com.FactoryOS.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class AuthResponse {
    private UUID userId;
    private UUID orgId;
    private String role;
    private String token;
}
