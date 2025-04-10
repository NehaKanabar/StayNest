package com.example.airBnb.demo.controller;

import com.example.airBnb.demo.dto.InventoryDto;
import com.example.airBnb.demo.dto.UpdateInventoryRequestDto;
import com.example.airBnb.demo.service.InventoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/inventory")
public class InventoryController {

    private final InventoryService inventoryService;


    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping("/rooms/{roomId}")
    public ResponseEntity<List<InventoryDto>> getAllInventoryByRoom(@PathVariable Long roomId)
    {
        return ResponseEntity.ok(inventoryService.getAllInventoyByRoom(roomId));
    }

    @PatchMapping("/room/{roomId}")
    public ResponseEntity<Void> updateInventory(@PathVariable Long roomId,
                                                @RequestBody UpdateInventoryRequestDto updateInventoryRequestDto)
    {
        inventoryService.updateInventory(roomId,updateInventoryRequestDto);
        return ResponseEntity.noContent().build();
    }

}
