package com.project.e_library.repo;

import com.project.e_library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepo extends JpaRepository<Book, Integer> {

    @Query(value = "SELECT * FROM books ORDER BY RANDOM() LIMIT :count", nativeQuery =true)
    public List<Book> getRandBooks(@Param("count") int count);
}
