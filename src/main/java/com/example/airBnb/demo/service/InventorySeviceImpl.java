package com.example.airBnb.demo.service;

import com.example.airBnb.demo.entity.Inventory;
import com.example.airBnb.demo.entity.Room;
import com.example.airBnb.demo.repository.InventoryRepository;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class InventorySeviceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    public InventorySeviceImpl(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
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
        LocalDate today = LocalDate.now();
        inventoryRepository.deleteByRoom(room);
    }
}
