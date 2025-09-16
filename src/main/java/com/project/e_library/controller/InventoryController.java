package com.project.e_library.controller;

import com.project.e_library.model.Book;
import com.project.e_library.service.InventoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<Book>> getUserBooks(Authentication authentication) {
        String authId = authentication.getName();
        return new ResponseEntity<>(inventoryService.getBooksByAuthId(authId),HttpStatus.OK);
    }

    @PostMapping("/inventory/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void addBook(@RequestParam("bookId") Integer bookId,
                                        Authentication authentication) {
        if (bookId == null) {
            throw new IllegalArgumentException("Book ID is required");
        }
        String authId = authentication.getName();
        inventoryService.addBookToInventory(authId,bookId);
    }

    @DeleteMapping("/inventory/remove")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeBookById(@RequestParam("bookId") Integer bookId,
                                            Authentication authentication) {
        String authId = authentication.getName();
        inventoryService.removeBookByAuthId(authId, bookId);
    }
}
