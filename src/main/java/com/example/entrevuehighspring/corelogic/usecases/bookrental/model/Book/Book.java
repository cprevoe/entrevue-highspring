package com.example.entrevuehighspring.corelogic.usecases.bookrental.model.Book;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class Book {
    private UUID id;
    private String title;
    private String authors[];
    private int minAge;
}