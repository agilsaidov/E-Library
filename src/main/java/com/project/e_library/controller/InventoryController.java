package com.project.e_library.controller;

import com.project.e_library.model.Book;
import com.project.e_library.repo.InventoryRepo;
import com.project.e_library.service.InventoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class InventoryController {
    InventoryService inventoryService;
    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping("/inventory")
    public List<Book> getBooksByUserId(@RequestParam("id") Long userId) {
        return inventoryService.getBooksByUserId(userId);
    }

    @DeleteMapping("/inventory/remove")
    public void removeBookById(@RequestParam("id") Long userId, @RequestParam("bookId") Long bookId) {
        inventoryService.removeBookById(userId, bookId);
    }
}
