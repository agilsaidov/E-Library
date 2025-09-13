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
    private final BookRepo bookRepo;
    private static final int PAGE_SIZE = 10;

    public BookService(BookRepo bookRepo) {
        this.bookRepo = bookRepo;
    }


    public List<Book> getRandomBooks(int count) {
        return bookRepo.getRandBooks(count);
    }


    public List<Book> advancedSearch(@Param("keyword") String keyword, int page) {
        return bookRepo.advancedSearch(keyword, PageRequest.of(Math.max(page, 0), PAGE_SIZE));
    }


    public List<Book> searchBooks(@Param("keyword") String keyword, int page) {
        return bookRepo.searchBook(keyword, PageRequest.of(Math.max(page,0), PAGE_SIZE));
    }


    public List<Book> filterByGenres(List<String> genres, int page) {
        List<String> normalizedGenres = genres.stream()
                .map(genre -> genre.toLowerCase().trim())
                .collect(Collectors.toList());

        return bookRepo.filterByGenres(normalizedGenres, PageRequest.of(Math.max(page,0),PAGE_SIZE));
    }
}
