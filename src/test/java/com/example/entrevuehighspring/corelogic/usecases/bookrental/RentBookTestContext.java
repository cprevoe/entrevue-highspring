package com.example.entrevuehighspring.corelogic.usecases.bookrental;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Optional;
import java.util.UUID;

import com.example.entrevuehighspring.corelogic.usecases.bookrental.dto.BookRentalRequestDto;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.dto.BookRentalResponseDto;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.event.EmailOnRentalRequestListener;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.port.BookRepository;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.port.LocationRepository;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.port.MockBookRepository;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.port.MockUserRepository;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.port.UserRepository;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.validator.AvailabilityValidator;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.validator.BookNotAvailableException;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.validator.RentalRequestAgeValidator;
import com.example.entrevuehighspring.domain.Book;
import com.example.entrevuehighspring.domain.BookId;
import com.example.entrevuehighspring.domain.Location;
import com.example.entrevuehighspring.domain.LocationId;
import com.example.entrevuehighspring.domain.MockBook;
import com.example.entrevuehighspring.domain.MockLocation;
import com.example.entrevuehighspring.domain.MockRentableState;
import com.example.entrevuehighspring.domain.MockUser;
import com.example.entrevuehighspring.domain.RentableState;
import com.example.entrevuehighspring.domain.RentableStatus;
import com.example.entrevuehighspring.domain.User;

import lombok.Builder;
import lombok.Getter;

@Builder
public class RentBookTestContext {
    @Getter private MockUser knownUser;
    @Getter private MockLocation knownLocation;
    @Getter private MockBook knownBook;

    @Builder.Default
    private MockLocation otherLocation = MockLocation.builder().id(LocationId.builder().id(UUID.randomUUID()).build()).build();

    @Builder.Default
    private boolean isBookAvailable = true;
    
    @Builder.Default
    private boolean isBookAtKnownLocation = true;

    @Builder.Default
    private Optional<BookRentalResponseDto> response = Optional.empty();

    @Builder.Default
    private Optional<Exception> exception = Optional.empty();

    public RentBookCommandHandler getHandler() {
         return RentBookCommandHandler.builder()
            .userRepository(getUserRepository())
            .bookRepository(getBookRepository())
            .locationRepository(getLocationRepository())
            .rentalRequestValidators(new LinkedList<>(Arrays.asList(
                new RentalRequestAgeValidator(),
                new AvailabilityValidator()
            )))
            .rentalRequestListeners(new LinkedList<>(Arrays.asList(
                new EmailOnRentalRequestListener()
            )))
            .build(); 
    }

    private UserRepository getUserRepository() {
        MockUserRepository userRepository = new MockUserRepository();
        if (this.knownUser != null) {
            userRepository.addUser(this.knownUser);
        }

        return userRepository;
    }

    private BookRepository getBookRepository() {
        MockBookRepository bookRepository = new MockBookRepository();
        if (this.knownBook != null) {
            MockRentableState rentableState = MockRentableState.builder()
                .rentable(this.knownBook)
                .borrower(Optional.empty())
                .status(this.isBookAvailable ? RentableStatus.AVAILABLE : RentableStatus.BORROWED)
                .build();

            this.knownBook.setLocation(this.isBookAtKnownLocation ? this.knownLocation : this.otherLocation);
            this.knownBook.setState(rentableState);
            bookRepository.addBook(this.knownBook);
        }
        return bookRepository;
    }

    private LocationRepository getLocationRepository() {
        MockLocationRepository locationRepository = new MockLocationRepository();

        if (this.knownLocation != null) {
            locationRepository.add(this.knownLocation);
        }
        return locationRepository;
    }

    public void rent(User user, Book book, Location location) {
        BookRentalRequestDto request = BookRentalRequestDto.builder()
            .userId(user.getId())
            .bookId((BookId)book.getId())
            .locationId(location.getId())
            .build();

        RentBookCommandHandler rentBookCommandHandler = getHandler();
        try {
            this.response = Optional.of(rentBookCommandHandler.requestToRentBook(request));
        } 
        catch (NullPointerException npe) {
            npe.printStackTrace();
            throw npe;
        }
        catch (Exception e) {
            this.exception = Optional.of(e);
        }
    }

    public void assertResponseGranted() {
        assertThat(this.response.isPresent())
            .isTrue();
        
        assertThat(this.response.get().isGranted())
            .isTrue();
    }

    public void assertExceptionExists() {
        assertThat(this.exception.isPresent()).isTrue();
    }

    public void assertExceptionInstanceOf(Class<? extends Exception> exceptionClass) {
        assertThat(this.exception.isPresent()).isTrue();
        try {
        assertThat(this.exception.get().getClass()).isEqualTo(exceptionClass);
        }
        catch (Exception e) {
            this.exception.get().printStackTrace();
            throw new RuntimeException("Unexpected exception", this.exception.get());
        }
    }

    public void assertKnownBookIsNotAvailableAtKnownLocation() {
        BookNotAvailableException bookNotAvailableException= null;

        try {
            new AvailabilityValidator().validate(knownUser, knownBook, knownLocation);
        } catch (BookNotAvailableException e) {
            bookNotAvailableException = e;
        } catch (Exception e) {
            // Rethrow any other unexpected exceptions.
            throw new RuntimeException("Unexpected!", e);
        }

        assertThat(bookNotAvailableException).isNotNull();
    }

}
