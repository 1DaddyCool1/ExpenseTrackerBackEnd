package com.webService.config;

import com.webService.model.AppUser;
import com.webService.model.Role;
import com.webService.repository.AppUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if(appUserRepository.findByUsername("admin").isEmpty()) {
            AppUser appUser = new AppUser();
            appUser.setFullName("Admin User");
            appUser.setUsername("admin");
            appUser.setPassword(passwordEncoder.encode("admin123"));
            appUser.setRole(Role.ADMIN);
            appUserRepository.save(appUser);
            System.out.println("Admin has been created: username= 'admin' password= 'admin123'");
        }
    }
}
