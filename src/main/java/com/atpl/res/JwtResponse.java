package com.atpl.res;

import lombok.Data;

@Data
public class JwtResponse {
    private int statusCode;
    private String token;
}
