package com.example.entrevuehighspring.corelogic.usecases.bookrental.service.validation;

import com.example.entrevuehighspring.corelogic.usecases.bookrental.model.Book;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.model.Location;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.model.Renter;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.service.exception.RentalValidationException;


/**
 * Interface for Validators checking the validity of Rental Requests.
 * 
 * Note: An alternative (and preferred implementation) returns validation status objects,
 *       then checks them in RentbookCommandHandler ; this allows all validations errors to
 *       be provided at once to a user.
 */
public interface RentalValidator {

    /**
     * Validation hook which should either pass transparently or throw a RentalValidationException
     * @param location The location in the request
     * @param renter The renter in the request
     * @param book The book the renter would like to rent
     * 
     * @throws RentalValidationException When the valiation fails
     */
    public void validateRental(Location location, Renter renter, Book book) throws RentalValidationException;
}
