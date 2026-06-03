package com.rasanusantara.ourrecipe.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty;

// ABSTRACTION: Kelas ini abstrak, tidak bisa dibuat objeknya secara langsung.
@Entity
@Table(name = "accounts")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "role_type", discriminatorType = DiscriminatorType.STRING)
public abstract class Account {

    // ENCAPSULATION: Semua atribut di-set private
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(nullable = false)
    private String username;

    // POLYMORPHISM: Method abstrak yang akan di-override oleh class turunannya
    public abstract String getRolePermissions();

    // Return role type berdasarkan class instance
    @com.fasterxml.jackson.annotation.JsonProperty("role_type")
    public String getRoleType() {
        if (this instanceof Admin) {
            return "ADMIN";
        } else if (this instanceof RegularUser) {
            return "USER";
        }
        return "UNKNOWN";
    }

    // ENCAPSULATION: Menggunakan Getter & Setter untuk mengakses data private
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
}
