package com.example.entrevuehighspring.domain;

public interface Book extends Rentable {

    BookType getType();

    int getMinAge();

    RentableId getId();

}