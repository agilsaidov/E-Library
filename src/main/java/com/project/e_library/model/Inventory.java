package com.project.e_library.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "inventory")
public class Inventory {

    @Id
    @Column(name = "user_id")
    private String userId;

    @Column(name = "book_id")
    private Integer bookId;

}
