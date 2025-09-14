package com.project.e_library.repo;

import com.project.e_library.model.Book;
import com.project.e_library.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryRepo extends JpaRepository<Inventory, Long> {

    @Query("SELECT b FROM Inventory i JOIN Book b ON i.bookId = b.bookId WHERE i.userId = :userId")
    List<Book> getBooksByUserId(Long userId);

}
