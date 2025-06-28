package com.webService.service;

import com.webService.model.AppUser;

import java.util.Optional;

public interface AppUserService {
    AppUser findByUsername(String username);
    AppUser save(AppUser appUser);
    Optional<AppUser> findUserById(Long id);
}
