package com.project.e_library.service;

import com.project.e_library.model.Book;
import com.project.e_library.repo.InventoryRepo;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class InventoryService {
    private final InventoryRepo inventoryRepo;

    public InventoryService(InventoryRepo inventoryRepo) {
        this.inventoryRepo = inventoryRepo;
    }

    public List<Book> getBooksByAuthId(String authId) {
        return inventoryRepo.getBooksByAuthId(authId);
    }

    public void addBookToInventory(String authId, Integer bookId) {
        inventoryRepo.addBookToInventory(authId, bookId);
    }

    public void removeBookByAuthId(String authId, Integer bookId) {
        inventoryRepo.removeBookByAuthId(authId, bookId);
    }
}
