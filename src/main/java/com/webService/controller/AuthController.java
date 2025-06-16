package com.webService.controller;

import com.webService.dto.AppUserDTO;
import com.webService.dto.AuthDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody AppUserDTO appUserDTO) {
        return new ResponseEntity<>("User registered successfully (implementation pending)", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthDTO authDTO) {
        return new ResponseEntity<>("Login successful (implementation pending)", HttpStatus.OK);
    }
}
