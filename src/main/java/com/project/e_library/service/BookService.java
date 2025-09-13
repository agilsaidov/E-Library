package com.project.e_library.service;

import com.project.e_library.exception.InvalidSearchParameterException;
import com.project.e_library.model.Book;
import com.project.e_library.repo.BookRepo;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

        validateKeyword(keyword, 200);
        return bookRepo.advancedSearch(keyword, PageRequest.of(Math.max(page, 0), PAGE_SIZE));
    }


    public List<Book> searchBooks(@Param("keyword") String keyword, int page) {

        validateKeyword(keyword,100);
        return bookRepo.searchBook(keyword, PageRequest.of(Math.max(page,0), PAGE_SIZE));
    }


    public List<Book> filterByGenres(List<String> genres, int page) {
        if (genres == null || genres.isEmpty()) {
            throw new InvalidSearchParameterException("Genres list cannot be empty");
        }

        if (genres.size() > 10) {
            throw new InvalidSearchParameterException("Cannot filter by more than 10 genres");
        }

        List<String> normalizedGenres = genres.stream()
                .filter(StringUtils::hasText)
                .map(genre -> genre.toLowerCase().trim())
                .distinct()
                .collect(Collectors.toList());
        return bookRepo.filterByGenres(normalizedGenres, PageRequest.of(Math.max(page,0),PAGE_SIZE));
    }

    private void validateKeyword(String keyword, int length) {

        if(keyword.trim().isEmpty()){
            throw new InvalidSearchParameterException("Search keyword cannot be empty");
        }
        if(keyword.length()>length){
            throw new InvalidSearchParameterException("Search keyword is too long(Max -> " + length + ")");
        }
    }
}
