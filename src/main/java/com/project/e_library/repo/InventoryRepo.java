package com.project.e_library.repo;

import com.project.e_library.model.Book;
import com.project.e_library.model.Inventory;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryRepo extends JpaRepository<Inventory, Long> {

    @Query("SELECT b FROM Inventory i JOIN Book b ON i.bookId = b.bookId " +
            "JOIN User u ON i.userId = u.userId WHERE u.authId = :authId")
    List<Book> getBooksByAuthId(String authId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Inventory i WHERE i.userId = " +
            "(SELECT u.userId FROM User u WHERE u.authId = :authId) " +
            "AND i.bookId = :bookId")
    void removeBookByAuthId(String authId, Integer bookId);


    @Modifying
    @Transactional
    @Query(value = "INSERT INTO inventory(user_id, book_id) " +
            "SELECT u.user_id, :bookId FROM users u WHERE u.authId = :authId",
          nativeQuery = true )
    void addBookToInventory(String authId, Integer bookId);

}
