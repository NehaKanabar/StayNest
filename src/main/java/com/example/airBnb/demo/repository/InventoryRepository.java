package com.example.airBnb.demo.repository;
import com.example.airBnb.demo.entity.Inventory;
import com.example.airBnb.demo.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface InventoryRepository extends JpaRepository<Inventory,Long> {
    void deleteByRoom(Room room);
}
