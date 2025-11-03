package com.example.entrevuehighspring.corelogic.usecases.bookrental.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.entrevuehighspring.corelogic.usecases.bookrental.dto.RentRequestDTO;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.dto.RentResponseDTO;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.model.Book;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.model.Location;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.model.PhysicalBook;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.model.Renter;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.persistence.BookRepository;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.persistence.LocationRepository;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.persistence.PhysicalBookRepository;
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

/**
 * Service for renting books
 */
@Builder
public class RentBookCommandHandler {
    
    /** Repository of known renters. */
    @Setter @Autowired RenterRepository renterRepo;

    /** Repository of known books (essentially book types). */
    @Setter @Autowired BookRepository bookRepo;
    
    /** Repository of known locations. */
    @Setter @Autowired LocationRepository locationRepo;

    /** Repository of physical books */
    @Setter @Autowired PhysicalBookRepository physicalBookRepository;

    /** Rental request validators. */
    @Setter @Autowired RentalValidator rentalValidators[];

    /** Rental Event Listeners */
    @Setter @Autowired RentalListener rentalListeners[];

    /**
     * Fields a request for a renter to rent a specified book from a specified location
     * @param rentRequest The request details
     * @return A response to the request indicating if the request was granted
     * @throws RenterDoesNotExistException If the renter indicated does not exist
     * @throws BookDoesNotExistException If the book indicated does not exist
     * @throws LocationDoesNotExistException If the location indicated does not exist
     * @throws BookNotAvailableException If the book is not available
     * @throws RentalValidationException If one of the rental rules was unable to validate this request
     */
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
        PhysicalBook physicalBook = physicalBookRepository
                                        .rentFirstAvailable(location, book, renter)
                                        .orElseThrow(() -> new BookNotAvailableException());
        
        for (RentalListener listener : Optional.ofNullable(rentalListeners).orElse(new RentalListener[] {})) {
            // Emails and future granted hooks happen here
            listener.rentalGranted(physicalBook);
        }

        RentResponseDTO result = RentResponseDTO.builder().isRentGranted(true).build();

        return result;
    }

}
