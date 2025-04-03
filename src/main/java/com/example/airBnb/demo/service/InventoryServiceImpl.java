package com.example.airBnb.demo.service;

import com.example.airBnb.demo.dto.HotelDto;
import com.example.airBnb.demo.dto.HotelPriceDto;
import com.example.airBnb.demo.dto.HotelSearchRequest;
import com.example.airBnb.demo.entity.Hotel;
import com.example.airBnb.demo.entity.Inventory;
import com.example.airBnb.demo.entity.Room;
import com.example.airBnb.demo.repository.HotelMinPriceRepository;
import com.example.airBnb.demo.repository.InventoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class InventoryServiceImpl implements InventoryService {
    private static final Logger log = LoggerFactory.getLogger(BookingServiceImpl.class);
    private final InventoryRepository inventoryRepository;
    private  final ModelMapper modelMapper;
    private final HotelMinPriceRepository hotelMinPriceRepository;

    public InventoryServiceImpl(InventoryRepository inventoryRepository, ModelMapper modelMapper, HotelMinPriceRepository hotelMinPriceRepository) {
        this.inventoryRepository = inventoryRepository;
        this.modelMapper = modelMapper;
        this.hotelMinPriceRepository = hotelMinPriceRepository;
    }

    @Override
    public void initializeRoomForAYear(Room room) {
        LocalDate today = LocalDate.now();
        LocalDate endDate = today.plusYears(1);

        while (!today.isAfter(endDate)) {  // Use while loop instead of for
            Inventory inventory = new Inventory.InventoryBuilder()
                    .hotel(room.getHotel())
                    .room(room)
                    .bookedCount(0)
                    .reservedCount(0)
                    .city(room.getHotel().getCity())
                    .date(today)
                    .price(room.getBasePrice())
                    .surgeFactor(BigDecimal.ONE)
                    .totalCount(room.getTotalCount())
                    .closed(false)
                    .build();

            inventoryRepository.save(inventory);

            today = today.plusDays(1); // Increment today properly
        }
    }

    @Override
    public void deleteAllInventories(Room room) {
        log.info("Deleting the inventories of room with id:{}",room.getId());
        LocalDate today = LocalDate.now();
        inventoryRepository.deleteByRoom(room);
    }

    @Override
    public Page<HotelPriceDto> searchHotels(HotelSearchRequest hotelSearchRequest) {
        log.info("Searching hotels for {} city from {}  to {}",hotelSearchRequest.getCity(),hotelSearchRequest.getStartDate(),hotelSearchRequest.getEndDate());
        Pageable pageable = PageRequest.of(hotelSearchRequest.getPage(), hotelSearchRequest.getSize());

        Long dateCount = ChronoUnit.DAYS.between(hotelSearchRequest.getStartDate(),hotelSearchRequest.getEndDate())+1;

        Page<HotelPriceDto> hotelPage = hotelMinPriceRepository.findHotelsWithAvailableInventory(hotelSearchRequest.getCity(),hotelSearchRequest.getStartDate(),hotelSearchRequest.getEndDate(),hotelSearchRequest.getRoomsCount(),dateCount,pageable);
        return hotelPage;
    }
}
