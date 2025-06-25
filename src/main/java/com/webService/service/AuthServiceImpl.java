package com.webService.service;

import com.webService.dto.AppUserDTO;
import com.webService.dto.AuthDTO;
import com.webService.dto.AuthResponseDTO;
import com.webService.model.AppUser;
import com.webService.utils.JWTUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final AppUserService appUserService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    public AuthServiceImpl(AppUserService appUserService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        this.appUserService = appUserService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public AuthResponseDTO registerUser(AppUserDTO appUserDTO) {
        if(appUserService.findByUsername(appUserDTO.getUsername())!=null){
            return new AuthResponseDTO(null, "Error user already exists");
        }
        AppUser appUser = new AppUser();
        appUser.setFullName(appUserDTO.getFullName());
        appUser.setUsername(appUserDTO.getUsername());
        appUser.setPassword(passwordEncoder.encode(appUserDTO.getPassword()));

        appUserService.save(appUser);

        AuthDTO authDTO = new AuthDTO();
        authDTO.setUsername(appUserDTO.getUsername());
        authDTO.setPassword(passwordEncoder.encode(appUserDTO.getPassword()));

        return loginUser(authDTO);
    }

    @Override
    public AuthResponseDTO loginUser(AuthDTO authDTO) {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    authDTO.getUsername(),
                                    authDTO.getPassword())
                    );

            final String token = jwtUtil.generateToken(
                    authDTO.getUsername());

            return new AuthResponseDTO(token, "success");
        } catch (BadCredentialsException e) {
            return new AuthResponseDTO(null, "Invalid username or password");
        }
    }
}
