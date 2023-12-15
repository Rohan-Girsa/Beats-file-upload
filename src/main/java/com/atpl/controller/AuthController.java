package com.atpl.controller;


import com.atpl.req.LoginRequest;
import com.atpl.res.JwtResponse;
import com.atpl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/api/auth/login", produces = "application/json")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest loginRequest) {
        JwtResponse response = new JwtResponse();
        try {
            response = userService.loginUser(loginRequest);
            return new ResponseEntity<JwtResponse>(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatusCode(500);
            response.setToken("Internal Server Error");
            return new ResponseEntity<JwtResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/api/welcome")
    public String welcome() {
        return "Hello World";
    }
}
