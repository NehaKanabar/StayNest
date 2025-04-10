package com.example.airBnb.demo.service;

import com.example.airBnb.demo.dto.HotelDto;
import com.example.airBnb.demo.dto.RoomDto;
import com.example.airBnb.demo.dto.HotelInfoDto;
import com.example.airBnb.demo.entity.Hotel;
import com.example.airBnb.demo.entity.Room;
import com.example.airBnb.demo.entity.User;
import com.example.airBnb.demo.exception.ResourceNotFoundException;
import com.example.airBnb.demo.exception.UnAuthorisedException;
import com.example.airBnb.demo.repository.HotelRepository;
import com.example.airBnb.demo.repository.RoomRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.airBnb.demo.util.AppUtils.getCurrentUser;

@Service
public class HostelServiceImpl implements HotelService{

    private static final Logger log = LoggerFactory.getLogger(HostelServiceImpl.class);
    private final HotelRepository hotelRepository;
    private final ModelMapper modelMapper;
    private final HotelDto hotelDto;
    private final InventoryService inventoryService;
    private final RoomRepository  roomRepository;

    public HostelServiceImpl(HotelRepository hotelRepository, ModelMapper modelMapper, HotelDto hotelDto, InventoryService inventoryService, RoomRepository roomRepository) {
        this.hotelRepository = hotelRepository;
        this.modelMapper = modelMapper;
        this.hotelDto = hotelDto;
        this.inventoryService = inventoryService;
        this.roomRepository = roomRepository;
    }

    @Override
    @Transactional
    public void deleteHotelById(Long id) {
        Hotel hotel = hotelRepository
                .findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Hotel not found with is: "+id));
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(!user.equals(hotel.getOwner()))
        {
            throw new UnAuthorisedException("This user does not own this hotel with id: "+id);
        }


        for(Room room : hotel.getRooms())
        {
            inventoryService.deleteAllInventories(room);
            roomRepository.deleteById(room.getId());
        }
        hotelRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void activateHotel(Long hotelId) {
        Hotel hotel = hotelRepository
                .findById(hotelId)
                .orElseThrow(()->new ResourceNotFoundException("Hotel not found with ID: "+hotelId));

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(!user.equals(hotel.getOwner()))
        {
            throw new UnAuthorisedException("This user does not own this hotel with id: "+hotelId);
        }


        hotel.setActive(true);
        //assuming only do it once
        for(Room room : hotel.getRooms())
        {
            inventoryService.initializeRoomForAYear(room);
        }
    }

//    public method
    @Override
    public HotelInfoDto getHotelInfoById(Long hotelId) {
        Hotel hotel = hotelRepository
                .findById(hotelId)
                .orElseThrow(()-> new ResourceNotFoundException("Hotel not found with ID: "+hotelId));

        List<RoomDto> rooms = hotel.getRooms().stream().map((element)->modelMapper.map(element,RoomDto.class)).toList();
        return new HotelInfoDto(modelMapper.map(hotel,HotelDto.class),rooms);
    }

    @Override
    public List<HotelDto> getAllHotels() {
        User user = getCurrentUser();
        log.info("Getting all hotels for the admin user with ID: {}",user.getId());

        List<Hotel> hotels = hotelRepository.findByOwner(user);
        return hotels
                .stream()
                .map((element)->modelMapper.map(element,HotelDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public HotelDto createNewHotel(HotelDto hotelDto) {
        log.info("Creating a new hotel with name: {}",hotelDto.getName());
        Hotel hotel = modelMapper.map(hotelDto,Hotel.class);
        hotel.setActive(false);

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        hotel.setOwner(user);


        hotel = hotelRepository.save(hotel);
        log.info("Created a new hotel with ID: {}",hotel.getId());
        return modelMapper.map(hotel,HotelDto.class);
    }

    @Override
    public HotelDto getHotelById(Long id) {
       log.info("getting the hotel with id : {}",id);
        Hotel hotel = hotelRepository
                .findById(id)
                .orElseThrow(()->new ResourceNotFoundException("hotel not found with id: "+id));
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(!user.equals(hotel.getOwner()))
        {
               throw new UnAuthorisedException("This user does not own this hotel with id: "+id);
        }
        return modelMapper.map(hotel,HotelDto.class);
    }

    @Override
    public HotelDto updateHotelById(Long id, HotelDto hotelDto) {
        Hotel hotel = hotelRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("hotel not found with id:"+id));

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(!user.equals(hotel.getOwner()))
        {
            throw new UnAuthorisedException("This user does not own this hotel with id: "+id);
        }

        modelMapper.map(hotelDto,hotel);

        hotel.setId(id);
        hotel = hotelRepository.save(hotel);
        return modelMapper.map(hotel,HotelDto.class);
    }



}
