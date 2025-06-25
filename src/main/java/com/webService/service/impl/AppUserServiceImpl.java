package com.webService.service.impl;

import com.webService.model.AppUser;
import com.webService.repository.AppUserRepository;
import com.webService.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppUserServiceImpl implements AppUserService {

    private final AppUserRepository appUserRepository;

    @Autowired
    public AppUserServiceImpl(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public AppUser findByUsername(String username) {
        return appUserRepository.findByUsername(username).orElse(null);
    }

    @Override
    public AppUser save(AppUser appUser) {
        return appUserRepository.save(appUser);
    }
}
