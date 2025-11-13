package com.example.entrevuehighspring.corelogic.usecases.bookrental;

import static org.mockito.Mockito.after;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.entrevuehighspring.corelogic.usecases.bookrental.dto.BookRentalRequestDto;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.dto.BookRentalResponseDto;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.event.RentalRequestListener;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.port.BookRepository;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.port.LocationRepository;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.port.UserRepository;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.validator.BookNotAvailableException;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.validator.RentalRequestValidator;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.validator.RentalValidationException;
import com.example.entrevuehighspring.domain.Book;
import com.example.entrevuehighspring.domain.Location;
import com.example.entrevuehighspring.domain.User;

import lombok.Builder;

@Builder
public class RentBookCommandHandler {

    @Autowired
    private UserRepository userRepository;
    private BookRepository bookRepository;
    private LocationRepository locationRepository;

    @Autowired
    @Builder.Default
    private List<RentalRequestValidator> rentalRequestValidators = new LinkedList<>();
    
    @Autowired
    @Builder.Default
    private List<RentalRequestListener> rentalRequestListeners = new LinkedList<>();

    public BookRentalResponseDto requestToRentBook(BookRentalRequestDto request) 
            throws BookNotAvailableException, 
                   InvalidUserException,
                   InvalidLocationException,
                   RentalValidationException {
        Book book = bookRepository.getBookById(request.getBookId()).orElseThrow(() -> new BookNotAvailableException());
        User user = userRepository.getUserById(request.getUserId()).orElseThrow(() -> new InvalidUserException());
        Location location = locationRepository.getLocationById(request.getLocationId()).orElseThrow(() -> new InvalidLocationException());

        for (RentalRequestValidator rentalRequestValidator : this.rentalRequestValidators) {
            rentalRequestValidator.validate(user, book, location);
        }

        book.rentTo(user);

        for (RentalRequestListener listener : this.rentalRequestListeners) {
            listener.handleBookLoanedEvent(book);
        }

        return BookRentalResponseDto.builder().granted(true).build();
    }
}
