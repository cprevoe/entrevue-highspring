package com.example.entrevuehighspring.domain;

import lombok.Builder;
import lombok.Getter;

@Builder
public class MockBook implements Rentable, Book {
    @Getter private BookId id;
    @Getter private BookType type;

    @Builder.Default
    @Getter private int minAge = 0;

    @Override
    public RentableId getId() {
        return id;
    }
}