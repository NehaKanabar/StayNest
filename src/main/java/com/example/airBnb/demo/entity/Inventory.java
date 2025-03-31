//package com.example.airBnb.demo.entity;
//
//import jakarta.persistence.*;
//import lombok.*;
//import org.hibernate.annotations.CreationTimestamp;
//import org.hibernate.annotations.UpdateTimestamp;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//
//@Entity
//@Getter
//@Setter
//@Table(
//        uniqueConstraints = @UniqueConstraint(
//        name="unique_hotel_room_date",
//        columnNames = {"hotel_id","room_id","date"}
//
//))
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//public class Inventory {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne(fetch=FetchType.LAZY)
//    @JoinColumn(name="hotel_id",nullable = false)
//    private Hotel hotel;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name="room_id",nullable = false)
//    private Room room;
//
//    @Column(nullable = false)
//    private LocalDate date;
//
//    @Column(nullable = false,columnDefinition = "INTEGER DEFAULT 0")
//    private Integer bookedCount;
//
//    @Column(nullable = false)
//    private Integer totalCount;
//
//    @Column(nullable = false,precision = 5,scale = 2)
//    private BigDecimal surgeFactor;
//
//    @Column(nullable = false,precision = 10,scale = 2)
//    private BigDecimal price; //basePrice * surgerFactor
//
//    @Column(nullable = false)
//    private String city;
//
//    @Column(nullable = false)
//    private Boolean closed;
//
//    @CreationTimestamp
//    private LocalDateTime createdAt;
//
//    @UpdateTimestamp
//    private LocalDateTime updatedAt;
//
//
//
//}
package com.example.airBnb.demo.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(
        uniqueConstraints = @UniqueConstraint(
                name = "unique_hotel_room_date",
                columnNames = {"hotel_id", "room_id", "date"}
        ))
public class Inventory {
    public Inventory(){}
    public Inventory(Long id, Hotel hotel, Room room, LocalDate date, Integer bookedCount, Integer totalCount, BigDecimal surgeFactor, BigDecimal price, String city, Boolean closed, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.hotel = hotel;
        this.room = room;
        this.date = date;
        this.bookedCount = bookedCount;
        this.totalCount = totalCount;
        this.surgeFactor = surgeFactor;
        this.price = price;
        this.city = city;
        this.closed = closed;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotel hotel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false, columnDefinition = "INTEGER DEFAULT 0")
    private Integer bookedCount;

    @Column(nullable = false)
    private Integer totalCount;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal surgeFactor;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price; // basePrice * surgeFactor

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private Boolean closed;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // **Private Constructor for Builder**
    private Inventory(InventoryBuilder builder) {
        this.id = builder.id;
        this.hotel = builder.hotel;
        this.room = builder.room;
        this.date = builder.date;
        this.bookedCount = builder.bookedCount;
        this.totalCount = builder.totalCount;
        this.surgeFactor = builder.surgeFactor;
        this.price = builder.price;
        this.city = builder.city;
        this.closed = builder.closed;
        this.createdAt = builder.createdAt;
        this.updatedAt = builder.updatedAt;
    }

    // **Static Builder Class**
    public static class InventoryBuilder {
        private Long id;
        private Hotel hotel;
        private Room room;
        private LocalDate date;
        private Integer bookedCount;
        private Integer totalCount;
        private BigDecimal surgeFactor;
        private BigDecimal price;
        private String city;
        private Boolean closed;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public InventoryBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public InventoryBuilder hotel(Hotel hotel) {
            this.hotel = hotel;
            return this;
        }

        public InventoryBuilder room(Room room) {
            this.room = room;
            return this;
        }

        public InventoryBuilder date(LocalDate date) {
            this.date = date;
            return this;
        }

        public InventoryBuilder bookedCount(Integer bookedCount) {
            this.bookedCount = bookedCount;
            return this;
        }

        public InventoryBuilder totalCount(Integer totalCount) {
            this.totalCount = totalCount;
            return this;
        }

        public InventoryBuilder surgeFactor(BigDecimal surgeFactor) {
            this.surgeFactor = surgeFactor;
            return this;
        }

        public InventoryBuilder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public InventoryBuilder city(String city) {
            this.city = city;
            return this;
        }

        public InventoryBuilder closed(Boolean closed) {
            this.closed = closed;
            return this;
        }

        public InventoryBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public InventoryBuilder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public Inventory build() {
            return new Inventory(this);
        }
    }

    // **Getter Methods**
    public Long getId() {
        return id;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public Room getRoom() {
        return room;
    }

    public LocalDate getDate() {
        return date;
    }

    public Integer getBookedCount() {
        return bookedCount;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public BigDecimal getSurgeFactor() {
        return surgeFactor;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getCity() {
        return city;
    }

    public Boolean getClosed() {
        return closed;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
