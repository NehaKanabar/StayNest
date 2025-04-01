package com.example.airBnb.demo.entity;
import com.example.airBnb.demo.entity.enums.BookingStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Booking {

     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;

     @ManyToOne(fetch = FetchType.LAZY)
     @JoinColumn(name = "hotel_id",nullable = false)
     private Hotel hotel;

     @ManyToOne(fetch = FetchType.LAZY)
     @JoinColumn(name = "room_id",nullable = false)
     private Room room;

     @ManyToOne(fetch = FetchType.LAZY)
     @JoinColumn(name="user_id",nullable = false)
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
             name="booking_guest",
             joinColumns = @JoinColumn(name="booking_id"),
             inverseJoinColumns = @JoinColumn(name="guest_id")

     )
     private Set<Guest> guests;

     @Column(nullable = false,precision = 10,scale=2)
     private BigDecimal amount;

     public Long getId() {
          return id;
     }

     public void setId(Long id) {
          this.id = id;
     }

     public Hotel getHotel() {
          return hotel;
     }

     public void setHotel(Hotel hotel) {
          this.hotel = hotel;
     }

     public Room getRoom() {
          return room;
     }

     public void setRoom(Room room) {
          this.room = room;
     }

     public User getUser() {
          return user;
     }

     public void setUser(User user) {
          this.user = user;
     }

     public Integer getRoomsCount() {
          return roomsCount;
     }

     public void setRoomsCount(Integer roomsCount) {
          this.roomsCount = roomsCount;
     }

     public LocalDate getCheckInDate() {
          return checkInDate;
     }

     public void setCheckInDate(LocalDate checkInDate) {
          this.checkInDate = checkInDate;
     }

     public LocalDate getCheckOutDate() {
          return checkOutDate;
     }

     public void setCheckOutDate(LocalDate checkOutDate) {
          this.checkOutDate = checkOutDate;
     }

     public LocalDateTime getCreatedAt() {
          return createdAt;
     }

     public void setCreatedAt(LocalDateTime createdAt) {
          this.createdAt = createdAt;
     }

     public LocalDateTime getUpdatedAt() {
          return updatedAt;
     }

     public void setUpdatedAt(LocalDateTime updatedAt) {
          this.updatedAt = updatedAt;
     }

     public BookingStatus getBookingStatus() {
          return bookingStatus;
     }

     public void setBookingStatus(BookingStatus bookingStatus) {
          this.bookingStatus = bookingStatus;
     }

     public Set<Guest> getGuests() {
          return guests;
     }

     public void setGuests(Set<Guest> guests) {
          this.guests = guests;
     }

     public BigDecimal getAmount() {
          return amount;
     }

     public void setAmount(BigDecimal amount) {
          this.amount = amount;
     }

     public Booking(Long id, Hotel hotel, Room room, User user, Integer roomsCount, LocalDate checkInDate, LocalDate checkOutDate, LocalDateTime createdAt, LocalDateTime updatedAt, BookingStatus bookingStatus, Set<Guest> guests, BigDecimal amount) {
          this.id = id;
          this.hotel = hotel;
          this.room = room;
          this.user = user;
          this.roomsCount = roomsCount;
          this.checkInDate = checkInDate;
          this.checkOutDate = checkOutDate;
          this.createdAt = createdAt;
          this.updatedAt = updatedAt;
          this.bookingStatus = bookingStatus;
          this.guests = guests;
          this.amount = amount;
     }
}
