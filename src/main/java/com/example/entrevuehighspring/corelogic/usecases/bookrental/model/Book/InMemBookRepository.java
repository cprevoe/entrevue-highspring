package com.example.entrevuehighspring.corelogic.usecases.bookrental.model.Book;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import lombok.Builder;

@Builder
public class InMemBookRepository implements BookRepository {
    private Map<UUID, Book> bookCatalogue;

    public Optional<Book> getBookById(UUID bookId) {
        return Optional.ofNullable(bookCatalogue.get(bookId));
    }

    public Optional<Book> addBook(Book book) {
        bookCatalogue.put(book.getId(), book);
        return Optional.of(book);
    }
}
