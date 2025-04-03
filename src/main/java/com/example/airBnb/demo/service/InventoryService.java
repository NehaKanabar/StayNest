package com.example.airBnb.demo.service;

import com.example.airBnb.demo.dto.HotelDto;
import com.example.airBnb.demo.dto.HotelPriceDto;
import com.example.airBnb.demo.dto.HotelSearchRequest;
import com.example.airBnb.demo.entity.Room;
import org.springframework.data.domain.Page;

public interface InventoryService {

    void initializeRoomForAYear(Room room);

    void deleteAllInventories(Room room);

    Page<HotelPriceDto> searchHotels(HotelSearchRequest hotelSearchRequest);
}
