package com.example.entrevuehighspring.corelogic.usecases.bookrental.port;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.example.entrevuehighspring.domain.Book;
import com.example.entrevuehighspring.domain.BookId;
import com.example.entrevuehighspring.domain.RentableId;

public class MockBookRepository implements BookRepository {
    Map<RentableId, Book> bookInventory = new HashMap<>();

    public void addBook(Book book) {
        this.bookInventory.put(book.getId(), book);
    }

    @Override
    public Optional<Book> getBookById(BookId bookId) {
        return Optional.of(this.bookInventory.get(bookId));
    }
}
