package com.example.airBnb.demo.service;

import com.example.airBnb.demo.dto.BookingDto;
import com.example.airBnb.demo.dto.BookingRequest;

public interface BookingService {

    BookingDto initialiseBooking(BookingRequest bookingRequest);
}
