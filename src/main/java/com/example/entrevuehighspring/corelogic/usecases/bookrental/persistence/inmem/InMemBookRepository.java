package com.example.entrevuehighspring.corelogic.usecases.bookrental.persistence.inmem;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import com.example.entrevuehighspring.corelogic.usecases.bookrental.model.Book;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.persistence.BookRepository;

import lombok.Builder;

/**
 * Implementation of {@link BookRepository}
 *
 * @see BookRepository
 */
@Builder
public class InMemBookRepository implements BookRepository {

    /**
     * The in-memory backing store of our book catalog indexed by ID.
     */
    private Map<UUID, Book> bookCatalog;

    public Optional<Book> getBookById(UUID bookId) {
        return Optional.ofNullable(bookCatalog.get(bookId));
    }

    public Optional<Book> addBook(Book book) {
        bookCatalog.put(book.getId(), book);
        return Optional.of(book);
    }

}
