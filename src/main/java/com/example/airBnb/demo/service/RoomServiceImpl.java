package com.example.airBnb.demo.service;

import com.example.airBnb.demo.dto.RoomDto;
import com.example.airBnb.demo.entity.Hotel;
import com.example.airBnb.demo.entity.Room;
import com.example.airBnb.demo.entity.User;
import com.example.airBnb.demo.exception.ResourceNotFoundException;
import com.example.airBnb.demo.exception.UnAuthorisedException;
import com.example.airBnb.demo.repository.HotelRepository;
import com.example.airBnb.demo.repository.RoomRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class RoomServiceImpl implements RoomService{


    private final RoomRepository roomRepository;
    private final ModelMapper modelMapper;
    private final HotelRepository hotelRepository;
    private final InventoryService inventoryService;

    public RoomServiceImpl(RoomRepository roomRepository, ModelMapper modelMapper, HotelRepository hotelRepository, InventoryService inventoryService) {
        this.roomRepository = roomRepository;
        this.modelMapper = modelMapper;
        this.hotelRepository = hotelRepository;
        this.inventoryService = inventoryService;
    }

    @Override
    public RoomDto createNewRoom(Long hotelId, RoomDto roomDto) {
        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(()->new ResourceNotFoundException("hotel not found with id: "+hotelId));

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(!user.equals(hotel.getOwner()))
        {
            throw new UnAuthorisedException("This user does not own this hotel with id: "+hotelId);
        }


        Room room = modelMapper.map(roomDto, Room.class);
        room.setHotel(hotel);
        room = roomRepository.save(room);
        if(hotel.getActive())
        {
              inventoryService.initializeRoomForAYear(room);
        }
        return modelMapper.map(room, RoomDto.class);
    }

    @Override
    public List<RoomDto> getAllRoomsInHotel(Long hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(()->new ResourceNotFoundException("Hotel not found: "+hotelId));

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(!user.equals(hotel.getOwner()))
        {
            throw new UnAuthorisedException("This user does not own this hotel with id: "+hotelId);
        }
        return hotel.getRooms()
                .stream()
                .map((element)->modelMapper.map(element,RoomDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public RoomDto getRoomById(Long roomId) {
       Room room  = roomRepository.findById(roomId)
               .orElseThrow(()->new ResourceNotFoundException("Room not found with id: "+roomId));
       return  modelMapper.map(room,RoomDto.class);
    }

    @Override
    @Transactional
    public void deleteRoomById(Long roomId) {
        Room room  = roomRepository.findById(roomId)
                .orElseThrow(()->new ResourceNotFoundException("Room not found with id: "+roomId));

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(!user.equals(room.getHotel().getOwner()))
        {
            throw new UnAuthorisedException("This user does not own this room with id: "+roomId);
        }

        inventoryService.deleteAllInventories(room);
        roomRepository.deleteById(roomId);
    }
}
