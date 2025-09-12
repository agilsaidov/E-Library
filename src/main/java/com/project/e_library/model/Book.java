package com.project.e_library.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;


import java.util.Objects;
import java.util.Set;

@Getter
@Setter(AccessLevel.NONE)
@Entity
@Table(name = "books")
public class Book {

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "author",columnDefinition = "TEXT")
    private String author;

    @ElementCollection(fetch = FetchType.LAZY)
    @BatchSize(size = 10)
    @CollectionTable(name = "genres", joinColumns = @JoinColumn(name = "book_id"))
    @Column(name = "genre")
    private Set<String> genres;

    @Column(name = "description",columnDefinition = "TEXT")
    private String description;

    @Column(name = "coverimg")
    private String imgUrl;

    @Column(name = "likedpercent")
    private String ratingPercent;

    @Column(name = "rating")
    private Double rating;

    @Column(name = "numratings")
    private Integer ratingNumber;

    @Column(name = "pages")
    private Integer pages;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id == book.id && Objects.equals(title, book.title) && Objects.equals(author, book.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, author);
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", rating=" + rating +
                '}';
    }
}
