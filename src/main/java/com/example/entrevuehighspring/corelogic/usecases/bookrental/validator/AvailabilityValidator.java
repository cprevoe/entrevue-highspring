package com.example.entrevuehighspring.corelogic.usecases.bookrental.validator;

import com.example.entrevuehighspring.domain.Book;
import com.example.entrevuehighspring.domain.Location;
import com.example.entrevuehighspring.domain.RentableStatus;
import com.example.entrevuehighspring.domain.User;

public class AvailabilityValidator implements RentalRequestValidator {

    @Override
    public void validate(User user, Book book, Location location) throws RentalValidationException {
        if (!book.getState().getStatus().equals(RentableStatus.AVAILABLE) || !book.getLocation().equals(location)) {
            // Someday this might check if the book can be moved between locations or something.
            // But today is not that day.
            throw new BookNotAvailableException();
        }
    }
    
}
