package com.example.airBnb.demo.repository;

import com.example.airBnb.demo.entity.Guest;
import com.example.airBnb.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GuestRepository extends JpaRepository<Guest, Long> {
    List<Guest> findByUser(User user);
}