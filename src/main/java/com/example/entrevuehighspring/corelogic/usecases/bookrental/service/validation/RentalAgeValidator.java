package com.example.entrevuehighspring.corelogic.usecases.bookrental.service.validation;

import org.springframework.stereotype.Component;

import com.example.entrevuehighspring.corelogic.usecases.bookrental.model.Book;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.model.Location;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.model.Renter;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.service.exception.RentalValidationException;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.service.exception.TooYoungForRentalException;

/**
 * A RentalValidator which validates that the book in question is age appropriate.
 * 
 * @see RentalValidator
 */
@Component
public class RentalAgeValidator implements RentalValidator {
    @Override
    public void validateRental(Location _location, Renter renter, Book book) throws RentalValidationException {
        if (book.getMinAge() > renter.getAge()) {
            throw new TooYoungForRentalException();
        }
        
    }
}
