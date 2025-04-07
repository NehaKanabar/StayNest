package com.example.airBnb.demo.service;

import com.example.airBnb.demo.entity.Booking;

public interface CheckoutService {

    String getCheckoutSession(Booking booking, String successUrl, String failureUrl);
}
