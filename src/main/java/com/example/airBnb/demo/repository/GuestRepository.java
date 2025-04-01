package com.example.airBnb.demo.repository;

import com.example.airBnb.demo.entity.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestRepository extends JpaRepository<Guest, Long> {
}