package com.project.e_library.service;

import com.project.e_library.model.Book;
import com.project.e_library.repo.BookRepo;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {
    BookRepo bookRepo;

    public BookService(BookRepo bookRepo) {
        this.bookRepo = bookRepo;
    }


    public List<Book> getRandomBooks(int count) {
        return bookRepo.getRandBooks(count);
    }


    public List<Book> advancedSearch(@Param("keyword") String keyword, int page, int size) {
        return bookRepo.advancedSearch(keyword, PageRequest.of(Math.max(page, 0), Math.max(size, 1)));
    }


    public List<Book> searchBooks(@Param("keyword") String keyword, int page, int size) {
        return bookRepo.searchBook(keyword, PageRequest.of(Math.max(page,0), Math.max(size, 1)));
    }


    public List<Book> filterByGenres(List<String> genres, int page, int size) {
        List<String> normalizedGenres = genres.stream()
                .map(genre -> genre.toLowerCase().trim())
                .collect(Collectors.toList());

        return bookRepo.filterByGenres(normalizedGenres, PageRequest.of(Math.max(page,0),Math.max(size, 1)));
    }
}
