package com.webService.service;

import com.webService.dto.AppUserDTO;
import com.webService.dto.AuthDTO;
import com.webService.dto.AuthResponseDTO;

public interface AuthService {
    AuthResponseDTO registerUser(AppUserDTO appUserDTO);
    AuthResponseDTO loginUser(AuthDTO authDTO);
}
