package com.example.airBnb.demo.repository;

import com.example.airBnb.demo.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}
