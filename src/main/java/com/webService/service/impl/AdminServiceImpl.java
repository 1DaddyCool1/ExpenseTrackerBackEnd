package com.webService.service.impl;

import com.webService.model.AppUser;
import com.webService.repository.AppUserRepository;
import com.webService.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    private final AppUserRepository appUserRepository;

    @Autowired
    public AdminServiceImpl(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public List<AppUser> getAllUsers() {
        return appUserRepository.findAll();
    }
}
