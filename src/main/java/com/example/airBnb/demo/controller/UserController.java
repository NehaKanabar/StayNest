package com.example.airBnb.demo.controller;

import com.example.airBnb.demo.dto.BookingDto;
import com.example.airBnb.demo.dto.ProfileUpdateRequestDto;
import com.example.airBnb.demo.service.BookingService;
import com.example.airBnb.demo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final BookingService bookingService;

    public UserController(UserService userService, BookingService bookingService) {
        this.userService = userService;
        this.bookingService = bookingService;
    }

    @PutMapping("/profile")
    public ResponseEntity<Void> updateProfile(@RequestBody ProfileUpdateRequestDto profileUpdateRequestDto)
    {
        userService.updateProfile(profileUpdateRequestDto);
        return  ResponseEntity.noContent().build();
    }

    @GetMapping("/myBookings")
    public ResponseEntity<List<BookingDto>> getMyBookings()
    {
        return ResponseEntity.ok(bookingService.getMyBookings());
    }

}
