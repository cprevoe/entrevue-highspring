package com.example.entrevuehighspring.corelogic.usecases.bookrental.service.validation;

import com.example.entrevuehighspring.corelogic.usecases.bookrental.model.Book;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.model.Location;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.model.Renter;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.service.exception.RentalValidationException;

public interface RentalValidator {
    public void validateRental(Location location, Renter renter, Book book) throws RentalValidationException;
}
