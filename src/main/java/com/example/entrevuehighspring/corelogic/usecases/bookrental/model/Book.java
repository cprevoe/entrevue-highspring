package com.example.entrevuehighspring.corelogic.usecases.bookrental.model;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents the relevant details of a Book shared by all copies of that book.
 *
 * For example the details shared by all copies of The Little Prince, 
 */
@Getter @Setter @Builder
public class Book {

    /**
     * The ID of the Book (could probably be the ISBN)
     */
    private UUID id;

    /**
     * The title of the book
     */
    private String title;

    /**
     * The authors of the book
     */
    private String authors[];

    /**
     * The recommended minimum age required to read this book.
     */
    private int minAge;

}
