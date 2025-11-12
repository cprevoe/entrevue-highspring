package com.example.entrevuehighspring.corelogic.usecases.bookrental.port;

import java.util.Optional;

import com.example.entrevuehighspring.domain.Book;
import com.example.entrevuehighspring.domain.BookId;

public interface BookRepository {

    Optional<Book> getBookById(BookId bookId);
    
}
