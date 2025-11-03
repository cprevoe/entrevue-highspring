package com.example.entrevuehighspring.corelogic.usecases.bookrental.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.entrevuehighspring.corelogic.usecases.bookrental.dto.RentRequestDTO;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.dto.RentResponseDTO;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.model.Book;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.model.Location;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.model.RentalState;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.model.RentalStatus;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.model.Renter;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.persistence.BookRepository;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.persistence.LocationRepository;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.persistence.RenterRepository;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.service.exception.BookDoesNotExistException;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.service.exception.BookNotAvailableException;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.service.exception.LocationDoesNotExistException;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.service.exception.RentalValidationException;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.service.exception.RenterDoesNotExistException;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.service.listeners.RentalListener;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.service.validation.RentalValidator;

import lombok.Builder;
import lombok.Setter;

@Builder
public class RentBookCommandHandler {
    
    @Setter
    @Autowired RenterRepository renterRepo;

    @Setter
    @Autowired BookRepository bookRepo;
    
    @Setter
    @Autowired LocationRepository locationRepo;

    @Setter
    @Autowired RentalValidator rentalValidators[];

    @Setter
    @Autowired RentalListener rentalListeners[];

    public RentResponseDTO rentRequest(RentRequestDTO rentRequest) 
        throws RenterDoesNotExistException, 
               BookDoesNotExistException, 
               LocationDoesNotExistException, 
               BookNotAvailableException,
               RentalValidationException {

        UUID bookId = rentRequest.getBookId();

        Renter renter = renterRepo
                          .getRenterById(rentRequest.getRenterId())
                          .orElseThrow(() -> new RenterDoesNotExistException());

        Book book = bookRepo
                      .getBookById(bookId)
                      .orElseThrow(() -> new BookDoesNotExistException());

        Location location = locationRepo
                              .getLocationById(rentRequest.getLocationId())
                              .orElseThrow(() -> new LocationDoesNotExistException());

        // Could also have validators return a status, then map to an exception if we want all
        // validators run regardless of success of others.
        for (RentalValidator validator : Optional.ofNullable(this.rentalValidators).orElse(new RentalValidator[] {})) {
            validator.validateRental(location, renter, book);
        }

        // At this point, the request is validated and the renter is permitted to rent the book.
        // We do not know if the book is available, we will effect the rent in the model to avoid
        // timing issues
        RentalState rentalState = location.rent(renter, book);

        // Read the results to see if the model was successful (otherwise we would have thrown an exception)
        boolean rentalGranted = false;
        if (rentalState.getRenter().equals(Optional.of(renter)) && rentalState.getStatus().equals(RentalStatus.Borrowed)) {
            rentalGranted = true;

            for (RentalListener listener : Optional.ofNullable(rentalListeners).orElse(new RentalListener[] {})) {
                // Emails and future granted hooks happen here
                listener.rentalGranted(rentalState);
            }

        }

        RentResponseDTO result = RentResponseDTO.builder().isRentGranted(rentalGranted).build();

        return result;
    }

}
