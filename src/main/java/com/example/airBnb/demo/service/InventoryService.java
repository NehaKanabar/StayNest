package com.example.airBnb.demo.service;

import com.example.airBnb.demo.dto.*;
import com.example.airBnb.demo.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface InventoryService {

    void initializeRoomForAYear(Room room);

    void deleteAllInventories(Room room);

    Page<HotelPriceDto> searchHotels(HotelSearchRequest hotelSearchRequest);


    List<InventoryDto> getAllInventoyByRoom(Long roomId);

    void updateInventory(Long roomId, UpdateInventoryRequestDto updateInventoryRequestDto);
}
