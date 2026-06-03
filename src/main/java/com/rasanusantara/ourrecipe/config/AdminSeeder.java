package com.rasanusantara.ourrecipe.config;

import com.rasanusantara.ourrecipe.model.Admin;
import com.rasanusantara.ourrecipe.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AdminSeeder implements CommandLineRunner {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public void run(String... args) throws Exception {
        String adminEmail = "admin@example.com";
        // Jangan membuat ulang jika sudah ada
        if (accountRepository.findByEmail(adminEmail).isEmpty()) {
            Admin admin = new Admin();
            admin.setEmail(adminEmail);
            // NOTE: aplikasi saat ini menyimpan password plain-text.
            // Ganti atau hash password ini sesuai kebutuhan keamanan.
            admin.setPassword("admin123");
            admin.setUsername("admin");
            accountRepository.save(admin);
            System.out.println("Admin user created: " + adminEmail);
        }
    }
}
