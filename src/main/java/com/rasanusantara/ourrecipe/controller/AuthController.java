package com.rasanusantara.ourrecipe.controller;

import com.rasanusantara.ourrecipe.model.Account;
import com.rasanusantara.ourrecipe.model.Admin;
import com.rasanusantara.ourrecipe.model.RegularUser;
import com.rasanusantara.ourrecipe.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AccountRepository accountRepository;

    // Endpoint simpel untuk Login
    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestParam String email, @RequestParam String password) {
        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email tidak terdaftar"));

        if (!account.getPassword().equals(password)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Password salah");
        }

        return ResponseEntity.ok(account);
    }

    // Endpoint simpel untuk Register User Biasa
    @PostMapping("/register")
    public ResponseEntity<Account> register(@RequestBody RegularUser newUser) {
        if (accountRepository.findByEmail(newUser.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email sudah terdaftar");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(accountRepository.save(newUser));
    }
}