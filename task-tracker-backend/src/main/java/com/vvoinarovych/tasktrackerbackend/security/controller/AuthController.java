package com.vvoinarovych.tasktrackerbackend.security.controller;

import com.vvoinarovych.tasktrackerbackend.security.dto.JwtResponse;
import com.vvoinarovych.tasktrackerbackend.security.dto.LoginRequest;
import com.vvoinarovych.tasktrackerbackend.security.dto.MessageResponse;
import com.vvoinarovych.tasktrackerbackend.security.dto.SignupRequest;
import com.vvoinarovych.tasktrackerbackend.security.services.AuthService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        JwtResponse jwtResponse = authService.authenticateUser(loginRequest);
        return ResponseEntity.ok(jwtResponse);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        authService.registerUser(signupRequest);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}