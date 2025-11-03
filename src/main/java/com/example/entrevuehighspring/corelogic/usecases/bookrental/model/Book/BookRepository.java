package com.example.entrevuehighspring.corelogic.usecases.bookrental.model.Book;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository of known books and how we can interact and track them.
 */
public interface BookRepository {

    /**
     * @param bookId The ID of the book of interest.
     * @return The details of the type of book.
     */
    public Optional<Book> getBookById(UUID bookId);

    /**
     * @param Book the book to add
     * @return The book added to the backing store which may or may not be the same book.
     */
    public Optional<Book> addBook(Book book);
}
