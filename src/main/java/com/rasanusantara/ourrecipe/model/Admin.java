package com.rasanusantara.ourrecipe.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

// INHERITANCE: Admin mewarisi sifat dari Account
@Entity
@DiscriminatorValue("ADMIN")
public class Admin extends Account {

    // POLYMORPHISM: Menimpa (Override) method dari parent class
    @Override
    public String getRolePermissions() {
        return "Memiliki akses untuk menghapus resep pengguna lain dan menghapus akun pengguna.";
    }
}