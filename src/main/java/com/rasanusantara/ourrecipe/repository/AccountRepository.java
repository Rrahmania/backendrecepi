package com.rasanusantara.ourrecipe.repository;

import com.rasanusantara.ourrecipe.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

// Menggunakan Generics (Bagian dari abstraksi)
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByEmail(String email);
}