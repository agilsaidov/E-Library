package com.project.e_library.controller;

import com.project.e_library.model.Book;
import com.project.e_library.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books")
    public ResponseEntity<List<Book>> getBooks() {
        return new ResponseEntity<>(bookService.getRandomBooks(10),HttpStatus.OK );
    }

    @GetMapping("/books/advancedsearch")
    public ResponseEntity<List<Book>> searchBooksAdvanced(@RequestParam String keyword,
                                                  @RequestParam(defaultValue = "0") int page) {
        List<Book> books = bookService.advancedSearch(keyword,page);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/books/search")
    public ResponseEntity<List<Book>> searchBooks(@RequestParam String keyword,
                                                  @RequestParam(defaultValue = "0") int page) {
        List<Book> books = bookService.searchBooks(keyword,page);
        return new ResponseEntity<>(books,HttpStatus.OK);
    }

    @PostMapping("/books/filter")
    public ResponseEntity<List<Book>> filterBooks(@RequestBody List<String> genres,
                                                  @RequestParam(defaultValue = "0")int page) {
        List<Book> books = bookService.filterByGenres(genres,page);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }
}
