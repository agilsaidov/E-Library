package com.project.e_library.service;

import com.project.e_library.model.Book;
import com.project.e_library.repo.BookRepo;
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
}
