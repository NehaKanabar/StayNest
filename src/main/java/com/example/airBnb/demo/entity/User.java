package com.example.airBnb.demo.entity;

import com.example.airBnb.demo.entity.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@Table(name="app_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true,nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    private String name;

    public User(){}

    public User(Long id, String email, String password, String name, Set<Role> roles) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.roles = roles;
    }

    @ElementCollection(fetch=FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

}
