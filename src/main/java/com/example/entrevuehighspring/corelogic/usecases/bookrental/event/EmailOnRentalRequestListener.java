package com.example.entrevuehighspring.corelogic.usecases.bookrental.event;

import org.springframework.stereotype.Component;

import com.example.entrevuehighspring.domain.Book;

@Component
public class EmailOnRentalRequestListener implements RentalRequestListener {

    @Override
    public void handleBookLoanedEvent(Book book) {
        this.queueEmailToBeSent(book);
    }

    public void queueEmailToBeSent(Book book) {
        // QUEUE (do not send) an email to be sent here.
    }
    
}
