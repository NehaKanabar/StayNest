package com.example.airBnb.demo.entity;

import com.example.airBnb.demo.entity.enums.BookingStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
public class Booking {

     public Booking() {}

     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;

     @ManyToOne(fetch = FetchType.LAZY)
     @JoinColumn(name = "hotel_id", nullable = false)
     private Hotel hotel;

     @ManyToOne(fetch = FetchType.LAZY)
     @JoinColumn(name = "room_id", nullable = false)
     private Room room;

     @ManyToOne(fetch = FetchType.LAZY)
     @JoinColumn(name = "user_id", nullable = false)
     private User user;

     @Column(nullable = false)
     private Integer roomsCount;

     @Column(nullable = false)
     private LocalDate checkInDate;

     @Column(nullable = false)
     private LocalDate checkOutDate;

     @CreationTimestamp
     private LocalDateTime createdAt;

     @UpdateTimestamp
     private LocalDateTime updatedAt;

     @Enumerated(EnumType.STRING)
     @Column(nullable = false)
     private BookingStatus bookingStatus;

     @ManyToMany(fetch = FetchType.LAZY)
     @JoinTable(
             name = "booking_guest",
             joinColumns = @JoinColumn(name = "booking_id"),
             inverseJoinColumns = @JoinColumn(name = "guest_id")
     )
     private Set<Guest> guests;

     @Column(nullable = false, precision = 10, scale = 2)
     private BigDecimal amount;

     @Column(unique = true)
     private String paymentSessionId;

     // --- Getters and Setters ---
     public Long getId() { return id; }
     public void setId(Long id) { this.id = id; }

     public Hotel getHotel() { return hotel; }
     public void setHotel(Hotel hotel) { this.hotel = hotel; }

     public Room getRoom() { return room; }
     public void setRoom(Room room) { this.room = room; }

     public User getUser() { return user; }
     public void setUser(User user) { this.user = user; }

     public Integer getRoomsCount() { return roomsCount; }
     public void setRoomsCount(Integer roomsCount) { this.roomsCount = roomsCount; }

     public LocalDate getCheckInDate() { return checkInDate; }
     public void setCheckInDate(LocalDate checkInDate) { this.checkInDate = checkInDate; }

     public LocalDate getCheckOutDate() { return checkOutDate; }
     public void setCheckOutDate(LocalDate checkOutDate) { this.checkOutDate = checkOutDate; }

     public LocalDateTime getCreatedAt() { return createdAt; }
     public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

     public LocalDateTime getUpdatedAt() { return updatedAt; }
     public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

     public BookingStatus getBookingStatus() { return bookingStatus; }
     public void setBookingStatus(BookingStatus bookingStatus) { this.bookingStatus = bookingStatus; }

     public Set<Guest> getGuests() { return guests; }
     public void setGuests(Set<Guest> guests) { this.guests = guests; }

     public BigDecimal getAmount() { return amount; }
     public void setAmount(BigDecimal amount) { this.amount = amount; }

     public String getPaymentSessionId() { return paymentSessionId; }
     public void setPaymentSessionId(String paymentSessionId) { this.paymentSessionId = paymentSessionId; }

     // --- Manual Builder ---
     public static class Builder {
          private Long id;
          private Hotel hotel;
          private Room room;
          private User user;
          private Integer roomsCount;
          private LocalDate checkInDate;
          private LocalDate checkOutDate;
          private LocalDateTime createdAt;
          private LocalDateTime updatedAt;
          private BookingStatus bookingStatus;
          private Set<Guest> guests;
          private BigDecimal amount;
          private String paymentSessionId;

          public Builder id(Long id) {
               this.id = id;
               return this;
          }

          public Builder hotel(Hotel hotel) {
               this.hotel = hotel;
               return this;
          }

          public Builder room(Room room) {
               this.room = room;
               return this;
          }

          public Builder user(User user) {
               this.user = user;
               return this;
          }

          public Builder roomsCount(Integer roomsCount) {
               this.roomsCount = roomsCount;
               return this;
          }

          public Builder checkInDate(LocalDate checkInDate) {
               this.checkInDate = checkInDate;
               return this;
          }

          public Builder checkOutDate(LocalDate checkOutDate) {
               this.checkOutDate = checkOutDate;
               return this;
          }

          public Builder createdAt(LocalDateTime createdAt) {
               this.createdAt = createdAt;
               return this;
          }

          public Builder updatedAt(LocalDateTime updatedAt) {
               this.updatedAt = updatedAt;
               return this;
          }

          public Builder bookingStatus(BookingStatus bookingStatus) {
               this.bookingStatus = bookingStatus;
               return this;
          }

          public Builder guests(Set<Guest> guests) {
               this.guests = guests;
               return this;
          }

          public Builder amount(BigDecimal amount) {
               this.amount = amount;
               return this;
          }

          public Builder paymentSessionId(String paymentSessionId) {
               this.paymentSessionId = paymentSessionId;
               return this;
          }

          public Booking build() {
               Booking booking = new Booking();
               booking.setId(this.id);
               booking.setHotel(this.hotel);
               booking.setRoom(this.room);
               booking.setUser(this.user);
               booking.setRoomsCount(this.roomsCount);
               booking.setCheckInDate(this.checkInDate);
               booking.setCheckOutDate(this.checkOutDate);
               booking.setCreatedAt(this.createdAt);
               booking.setUpdatedAt(this.updatedAt);
               booking.setBookingStatus(this.bookingStatus);
               booking.setGuests(this.guests);
               booking.setAmount(this.amount);
               booking.setPaymentSessionId(this.paymentSessionId);
               return booking;
          }
     }
}
