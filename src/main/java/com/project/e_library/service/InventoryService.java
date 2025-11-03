package com.project.e_library.service;

import com.project.e_library.model.Book;
import com.project.e_library.repo.InventoryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepo inventoryRepo;

    public List<Book> getBooksByUserId(String userId) {
        return inventoryRepo.getBooksByUserId(userId);
    }

    public void addBookToInventory(String userId, Integer bookId) {
        inventoryRepo.addBookToInventory(userId, bookId);
    }

    public void removeBookByAuthId(String userId, Integer bookId) {
        inventoryRepo.removeBookByUserId(userId, bookId);
    }
}
