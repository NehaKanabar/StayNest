package com.example.airBnb.demo.repository;

import com.example.airBnb.demo.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room,Long> {
}
