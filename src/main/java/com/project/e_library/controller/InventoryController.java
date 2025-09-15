package com.project.e_library.controller;

import com.project.e_library.model.Book;
import com.project.e_library.service.InventoryService;
import org.springframework.security.core.Authentication;
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
    public List<Book> getUserBooks(Authentication authentication) {
        String authId = authentication.getName();
        return inventoryService.getBooksByAuthId(authId);
    }

    @DeleteMapping("/inventory/remove")
    public void removeBookById(@RequestParam("bookId") Long bookId,
                               Authentication authentication) {
        String authId = authentication.getName();
        inventoryService.removeBookByAuthId(authId, bookId);
    }
}
