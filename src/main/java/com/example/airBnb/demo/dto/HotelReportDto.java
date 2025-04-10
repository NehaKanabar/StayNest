package com.example.airBnb.demo.dto;

import lombok.Data;

import java.math.BigDecimal;


public class HotelReportDto {
    private Long bookingCount;
    private BigDecimal totalRevenue;
    private BigDecimal avgRevenue;
    public HotelReportDto(){}

    public HotelReportDto(Long bookingCount, BigDecimal totalRevenue, BigDecimal avgRevenue) {
        this.bookingCount = bookingCount;
        this.totalRevenue = totalRevenue;
        this.avgRevenue = avgRevenue;
    }

    public Long getBookingCount() {
        return bookingCount;
    }

    public void setBookingCount(Long bookingCount) {
        this.bookingCount = bookingCount;
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public BigDecimal getAvgRevenue() {
        return avgRevenue;
    }

    public void setAvgRevenue(BigDecimal avgRevenue) {
        this.avgRevenue = avgRevenue;
    }
}
