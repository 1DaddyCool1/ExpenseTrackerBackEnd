package com.webService.controller;

import com.webService.dto.AppUserDTO;
import com.webService.dto.AuthDTO;
import com.webService.dto.AuthResponseDTO;
import com.webService.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponseDTO> signup(@RequestBody AppUserDTO appUserDTO) {
        AuthResponseDTO response = authService.registerUser(appUserDTO);
        if("success".equals(response.getMessage()))
        {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthDTO authDTO) {
        AuthResponseDTO response = authService.loginUser(authDTO);
        if("success".equals(response.getMessage()))
        {
            return ResponseEntity.ok(response);
        } else  {
            return ResponseEntity.badRequest().body(response);
        }
    }
}
