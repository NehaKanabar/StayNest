
package com.example.airBnb.demo.service;

import com.example.airBnb.demo.dto.BookingDto;
import com.example.airBnb.demo.dto.BookingRequest;
import com.example.airBnb.demo.dto.GuestDto;
import com.example.airBnb.demo.entity.*;
import com.example.airBnb.demo.entity.enums.BookingStatus;
import com.example.airBnb.demo.exception.ResourceNotFoundException;
import com.example.airBnb.demo.exception.UnAuthorisedException;
import com.example.airBnb.demo.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private static final Logger log = LoggerFactory.getLogger(BookingServiceImpl.class);
    private final BookingRepository bookingRepository;
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    private final InventoryRepository inventoryRepository;
    private final ModelMapper modelMapper;

    private final GuestRepository guestRepository;

    public BookingServiceImpl(BookingRepository bookingRepository, HotelRepository hotelRepository, RoomRepository roomRepository, InventoryRepository inventoryRepository,GuestRepository guestRepository, ModelMapper modelMapper) {
        this.bookingRepository = bookingRepository;
        this.hotelRepository = hotelRepository;
        this.roomRepository = roomRepository;
        this.inventoryRepository = inventoryRepository;
        this.guestRepository = guestRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public BookingDto initialiseBooking(BookingRequest bookingRequest) {

        log.info("Initialising Booking for hotel: {}, room: {}, date: {} - {}",
                bookingRequest.getHotelId(), bookingRequest.getRoomId(),
                bookingRequest.getCheckInDate(), bookingRequest.getCheckOutDate());

        Hotel hotel = hotelRepository.findById(bookingRequest.getHotelId())
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id: " + bookingRequest.getHotelId()));

        Room room = roomRepository.findById(bookingRequest.getRoomId())
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with id: " + bookingRequest.getRoomId()));

        List<Inventory> inventoryList = inventoryRepository.findAndLockAvailableInventory(
                room.getId(), bookingRequest.getCheckInDate(),
                bookingRequest.getCheckOutDate(), bookingRequest.getRoomsCount());

        long daysCount = ChronoUnit.DAYS.between(bookingRequest.getCheckInDate(), bookingRequest.getCheckOutDate()) + 1;

        if (inventoryList.size() != daysCount) {
            throw new IllegalStateException("Room is not available anymore");
        }

        // Reserve the room / update the booked count of inventories
        for (Inventory inventory : inventoryList) {
            inventory.setReservedCount(inventory.getReservedCount() + bookingRequest.getRoomsCount());
        }
        inventoryRepository.saveAll(inventoryList);


        // TODO: CALCULATE DYNAMIC PRICE
        Booking booking = new Booking(
                null,  // Assuming ID is auto-generated
                hotel,
                room,
                getCurrentUser(),
                bookingRequest.getRoomsCount(),
                bookingRequest.getCheckInDate(),
                bookingRequest.getCheckOutDate(),
                LocalDateTime.now(),  // createdAt (Auto-generate current timestamp)
                LocalDateTime.now(),  // updatedAt (Auto-generate current timestamp)
                BookingStatus.RESERVED,
                new HashSet<>(),  // Assuming an empty set of guests for now
                BigDecimal.TEN

        );


        booking = bookingRepository.save(booking);
        return modelMapper.map(booking, BookingDto.class);
    }

    @Override
    @Transactional
    public BookingDto addGuests(Long bookingId, List<GuestDto> guestDtoList) {
        log.info("Adding guest for booking with id: {}",bookingId);

        Booking booking = bookingRepository.findById(bookingId).orElseThrow(()->
                new ResourceNotFoundException("Booking not found with id: "+bookingId));

        User user = getCurrentUser();

        if(!user.equals(booking.getUser()))
        {
            throw new UnAuthorisedException("Booking does not belong to this user with id: "+user.getId());
        }


         if(hasBookingExpired(booking))
         {
             throw new IllegalStateException("Booking has already expired");
         }

         if(booking.getBookingStatus() != BookingStatus.RESERVED)
         {
             throw new IllegalStateException("Booking is not under reserved state, can not add guests");
         }

         for(GuestDto guestDto: guestDtoList)
         {
             Guest guest= modelMapper.map(guestDto, Guest.class);
             guest.setUser(getCurrentUser());
             guest = guestRepository.save(guest);
             booking.getGuests().add(guest);

         }
         booking.setBookingStatus(BookingStatus.GUEST_ADDED);
         booking = bookingRepository.save(booking);

         return modelMapper.map(booking,BookingDto.class);
    }

    public boolean hasBookingExpired(Booking booking)
    {
        return booking.getCreatedAt().plusMinutes(10).isBefore(LocalDateTime.now());
    }

    public User getCurrentUser()
    {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}

