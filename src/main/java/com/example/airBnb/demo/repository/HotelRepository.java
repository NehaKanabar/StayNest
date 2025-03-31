package com.example.airBnb.demo.repository;


import com.example.airBnb.demo.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel,Long> {
}
