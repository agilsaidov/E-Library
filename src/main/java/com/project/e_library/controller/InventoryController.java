package com.project.e_library.controller;

import com.project.e_library.model.Book;
import com.project.e_library.security.JwtService;
import com.project.e_library.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;
    private final JwtService jwtService;

    @GetMapping
    public ResponseEntity<List<Book>> getUserBooks(@RequestHeader("Authorization") String authHeader) {

        String userId = getUserIdFromAuth(authHeader);

        List<Book> bookList = inventoryService.getBooksByUserId(userId);
        return new ResponseEntity<>(bookList, HttpStatus.OK);
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void addBook(@RequestParam("bookId") Integer bookId,
                        @RequestHeader("Authorization") String authHeader) {

        String userId = getUserIdFromAuth(authHeader);
        inventoryService.addBookToInventory(userId,bookId);
    }

    @DeleteMapping("/remove")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeBookById(@RequestParam("bookId") Integer bookId,
                               @RequestHeader("Authorization") String authHeader) {

        String userId = getUserIdFromAuth(authHeader);
        inventoryService.removeBookByAuthId(userId, bookId);
    }


    //Helper Method
    private String getUserIdFromAuth(String authHeader) {
        return jwtService.getIdFromToken(authHeader.substring(7));
    }
}
