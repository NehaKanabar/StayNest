package com.example.airBnb.demo.controller;

import com.example.airBnb.demo.dto.BookingDto;
import com.example.airBnb.demo.dto.BookingRequest;
import com.example.airBnb.demo.dto.GuestDto;
import com.example.airBnb.demo.service.BookingService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @PostMapping("/{bookingId}/addGuests")
    public ResponseEntity<BookingDto> addGuests(@PathVariable Long bookingId,
                                                @RequestBody List<GuestDto> guestDtoList)
    {
        return ResponseEntity.ok(bookingService.addGuests(bookingId,guestDtoList));
    }

    @PostMapping("/{bookingId}/payments")
    public ResponseEntity<Map<String,String>> initiatePayment(@PathVariable Long bookingId)
    {
       String sessionUrl= bookingService.initiatePayments(bookingId);
       return ResponseEntity.ok(Map.of("sessionUrl",sessionUrl));
    }

    @PostMapping("{bookingId}/cancel")
    public ResponseEntity<Void> cancelBooking(@PathVariable Long bookingId)
    {
        bookingService.cancelBooking(bookingId);
       return  ResponseEntity.noContent().build();
    }

    @PostMapping("{bookingId}/status")
    public ResponseEntity<Map<String,String>> getBookingStatus(@PathVariable Long bookingId)
    {
        return ResponseEntity.ok(Map.of("status",bookingService.getBookingStatus(bookingId)));
    }
}
