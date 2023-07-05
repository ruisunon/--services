package com.rycoding.ecommerce.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Book {

    @Column(name = "book_name")
    private String bookName;

    @Column(name = "number_of_page")
    private String numberOfPage;

    @Column(name = "author")
    private String author;
}
