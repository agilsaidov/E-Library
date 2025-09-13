package com.project.e_library.repo;

import com.project.e_library.model.Book;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepo extends JpaRepository<Book, Integer> {

    @Query(value = "SELECT * FROM books ORDER BY RANDOM() LIMIT :count", nativeQuery =true)
    List<Book> getRandBooks(@Param("count") int count);


    @Query("SELECT b FROM Book b WHERE " +
            "LOWER(b.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(b.author) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(b.description) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "ORDER BY " +
            "CASE WHEN LOWER(b.title) = LOWER(:keyword) THEN 0 " +
            "WHEN LOWER(b.title) LIKE LOWER(CONCAT('%', :keyword, '%')) THEN 1 " +
            "WHEN LOWER(b.author) = LOWER(:keyword) THEN 2 " +
            "WHEN LOWER(b.author) LIKE LOWER(CONCAT('%', :keyword, '%')) THEN 3 "+
            "ELSE 4 END")
    List<Book> advancedSearch(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT b FROM Book b WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Book> searchBook(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT DISTINCT b FROM Book b "+
            "JOIN b.genres g "+
            "WHERE TRIM(LOWER(g)) IN :genres")
    List<Book> filterByGenres(@Param("genres") List<String> genres, Pageable pageable);
}
