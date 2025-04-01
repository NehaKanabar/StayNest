package com.example.airBnb.demo.controller;

import com.example.airBnb.demo.dto.BookingDto;
import com.example.airBnb.demo.dto.BookingRequest;
import com.example.airBnb.demo.service.BookingService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bookings")
public class HotelBookingController {
    public HotelBookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    private final BookingService bookingService;

    @PostMapping("/init")
    public ResponseEntity<BookingDto> initialiseBooking(@RequestBody BookingRequest bookingRequest)
    {
        return ResponseEntity.ok(bookingService.initialiseBooking(bookingRequest));
    }
}
