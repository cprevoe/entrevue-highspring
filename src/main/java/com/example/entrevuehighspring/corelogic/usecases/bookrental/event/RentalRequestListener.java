package com.example.entrevuehighspring.corelogic.usecases.bookrental.event;

import com.example.entrevuehighspring.domain.Book;

public interface RentalRequestListener {

    void handleBookLoanedEvent(Book book);

}
