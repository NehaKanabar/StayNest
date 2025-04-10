package com.example.airBnb.demo.controller;

import com.example.airBnb.demo.dto.BookingDto;
import com.example.airBnb.demo.dto.HotelDto;
import com.example.airBnb.demo.dto.HotelReportDto;
import com.example.airBnb.demo.service.BookingService;
import com.example.airBnb.demo.service.HotelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/admin/hotels")
@Slf4j
public class HotelController {

    private final HotelService hotelService;
    private final BookingService bookingService;

    public HotelController(HotelService hotelService, BookingService bookingService) {
        this.hotelService = hotelService;
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<HotelDto> createNewHotel (@RequestBody HotelDto hotelDto)
    {
     //   log.info("Attempting to create a new hotel with name:"+hotelDto.getName());
        HotelDto hotel = hotelService.createNewHotel(hotelDto);
        return new ResponseEntity<>(hotel, HttpStatus.CREATED);
    }

    @GetMapping("/{hotelId}")
    public ResponseEntity<HotelDto> getHotelById(@PathVariable Long hotelId)
    {
        HotelDto hotelDto = hotelService.getHotelById(hotelId);
        return ResponseEntity.ok(hotelDto);
    }

    @PutMapping("/{hotelId}")
    public ResponseEntity<HotelDto> updateHotelById(@PathVariable Long hotelId, @RequestBody HotelDto hotelDto)
    {
        HotelDto hotel = hotelService.updateHotelById(hotelId, hotelDto);
        return ResponseEntity.ok(hotel);
    }

    @DeleteMapping("/{hotelId}")
    public ResponseEntity<Void> deleteHotelById(@PathVariable Long hotelId)
    {
        hotelService.deleteHotelById(hotelId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{hotelId}/activate")
    public ResponseEntity<Void> activateHotel(@PathVariable Long hotelId)
    {
        hotelService.activateHotel(hotelId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<HotelDto>> getAllHotels()
    {
        return ResponseEntity.ok(hotelService.getAllHotels());
    }

    @GetMapping("/{hotelId}/bookings")
    public ResponseEntity<List<BookingDto>> getAllBookingsByHotelId(@PathVariable Long hotelId)
    {
        return ResponseEntity.ok(bookingService.getAllBoookingsByHotelId(hotelId));
    }

    @GetMapping("/{hotelId}/reports")
    public ResponseEntity<HotelReportDto> getHotelReport(@PathVariable Long hotelId,
                                                         @RequestParam(required = false)LocalDate startDate,
                                                         @RequestParam(required = false)LocalDate endDate)
    {
        if(startDate==null) startDate=LocalDate.now().minusMonths(1);
        if(endDate==null) endDate = LocalDate.now();

        return ResponseEntity.ok(bookingService.getHotelReport(hotelId,startDate,endDate));
    }
}

