package com.example.entrevuehighspring.corelogic.usecases.bookrental.validator;

import com.example.entrevuehighspring.domain.Book;
import com.example.entrevuehighspring.domain.Location;
import com.example.entrevuehighspring.domain.User;

// Represents a rental request validator
public interface RentalRequestValidator {

    public void validate(User user, Book book, Location location) throws RentalValidationException;

}
