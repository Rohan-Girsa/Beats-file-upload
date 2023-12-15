package com.atpl.service;

import com.atpl.req.LoginRequest;
import com.atpl.res.JwtResponse;

public interface UserService {
    void saveUsers(String email);

    JwtResponse loginUser(LoginRequest loginRequest);
}
