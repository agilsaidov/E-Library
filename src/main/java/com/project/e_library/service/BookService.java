package com.project.e_library.service;

import com.project.e_library.model.Book;
import com.project.e_library.repo.BookRepo;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    BookRepo bookRepo;
    public BookService(BookRepo bookRepo) {
        this.bookRepo = bookRepo;
    }

    public List<Book> getRandomBooks(int count) {
        return bookRepo.getRandBooks(count);
    }

    public List<Book> searchBooks(String keyword, int page, int size) {
        return bookRepo.searchBooks(keyword, PageRequest.of(Math.max(page, 0), Math.max(size, 1)));
    }

    public List<Book> filterByGenres(List<String> genres,int page, int size) {
        return bookRepo.filterByGenres(genres, PageRequest.of(Math.max(page,0),Math.max(size, 1)));
    }
}
