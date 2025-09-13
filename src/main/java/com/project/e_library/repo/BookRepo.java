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


    @Query(value = "SELECT b.* FROM books b WHERE " +
            "b.title ILIKE '%' || :keyword || '%' OR " +
            "b.author ILIKE '%' || :keyword || '%' OR " +
            "b.description ILIKE '%' || :keyword || '%' " +
            "ORDER BY " +
            "CASE " +
            "WHEN LOWER(b.title) = LOWER(:keyword) THEN 0 " +
            "WHEN b.title ILIKE '%' || :keyword || '%' THEN 1 " +
            "WHEN LOWER(b.author) = LOWER(:keyword) THEN 2 " +
            "WHEN b.author ILIKE '%' || :keyword || '%' THEN 3 " +
            "ELSE 4 " +
            "END",
            nativeQuery = true)
    List<Book> searchBooks(@Param("keyword") String keyword, Pageable pageable);
}
