package com.example.airBnb.demo.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class HotelInfoDto {
    public HotelInfoDto(){}

    public HotelDto getHotel() {
        return hotel;
    }

    public void setHotel(HotelDto hotel) {
        this.hotel = hotel;
    }

    public List<RoomDto> getRooms() {
        return rooms;
    }

    public void setRooms(List<RoomDto> rooms) {
        this.rooms = rooms;
    }

    public HotelInfoDto(HotelDto hotel, List<RoomDto> rooms) {
        this.hotel = hotel;
        this.rooms = rooms;
    }

    private HotelDto hotel;
    private List<RoomDto> rooms;


}
