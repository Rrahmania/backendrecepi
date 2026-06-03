package com.rasanusantara.ourrecipe.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

// INHERITANCE: RegularUser mewarisi sifat dari Account
@Entity
@DiscriminatorValue("USER")
public class RegularUser extends Account {

    // POLYMORPHISM: Menimpa (Override) method dengan output berbeda dari Admin
    @Override
    public String getRolePermissions() {
        return "Memiliki akses untuk membuat resep sendiri, mengedit resep sendiri, dan menyimpan favorit.";
    }
}