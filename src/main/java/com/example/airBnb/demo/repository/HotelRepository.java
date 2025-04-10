package com.example.airBnb.demo.repository;


import com.example.airBnb.demo.entity.Hotel;
import com.example.airBnb.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HotelRepository extends JpaRepository<Hotel,Long> {
    List<Hotel> findByOwner(User user);
}
