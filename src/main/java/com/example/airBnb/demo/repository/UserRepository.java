package com.example.airBnb.demo.repository;

import com.example.airBnb.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
