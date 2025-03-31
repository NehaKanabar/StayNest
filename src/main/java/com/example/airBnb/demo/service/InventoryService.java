package com.example.airBnb.demo.service;

import com.example.airBnb.demo.entity.Room;

public interface InventoryService {

    void initializeRoomForAYear(Room room);

    void deleteFutureInventories(Room room);
}
