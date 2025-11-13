package com.example.entrevuehighspring.domain;

public interface Book {

    BookType getType();

    int getMinAge();

    RentableId getId();

}