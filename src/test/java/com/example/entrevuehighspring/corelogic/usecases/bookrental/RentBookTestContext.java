package com.example.entrevuehighspring.corelogic.usecases.bookrental;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Optional;

import com.example.entrevuehighspring.corelogic.usecases.bookrental.dto.BookRentalRequestDto;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.dto.BookRentalResponseDto;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.port.BookRepository;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.port.LocationRepository;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.port.MockBookRepository;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.port.MockRentableRepository;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.port.MockUserRepository;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.port.UserRepository;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.validator.RentalRequestAgeValidator;
import com.example.entrevuehighspring.domain.Book;
import com.example.entrevuehighspring.domain.BookId;
import com.example.entrevuehighspring.domain.Location;
import com.example.entrevuehighspring.domain.RentableInventory;
import com.example.entrevuehighspring.domain.RentableState;
import com.example.entrevuehighspring.domain.User;

import lombok.Builder;
import lombok.Getter;

@Builder
public class RentBookTestContext {
    @Getter private User knownUser;
    @Getter private Location knownLocation;
    @Getter private Book knownBook;

    @Builder.Default
    private boolean isBookAvailable = true;

    @Builder.Default
    private Optional<MockRentableRepository> rentableRepository = Optional.empty();

    @Builder.Default
    private Optional<BookRentalResponseDto> response = Optional.empty();

    @Builder.Default
    private Optional<Exception> exception = Optional.empty();

    private MockRentableRepository getRentableRepository() {
        if (rentableRepository.isEmpty()) {
            rentableRepository = Optional.of(new MockRentableRepository());
            if (knownBook != null && knownLocation != null) {
                rentableRepository.get().addRentableState(
                    knownLocation, 
                    knownBook, 
                    RentableState.builder()
                        .rentable(knownBook)
                        .status(this.isBookAvailable ? RentableState.Status.AVAILABLE : RentableState.Status.BORROWED)
                        .build());
            }
        }

        return rentableRepository.get();
    }

    public RentBookCommandHandler getHandler() {
         return RentBookCommandHandler.builder()
            .rentableInventory(
                RentableInventory.builder()
                    .rentableRepository(getRentableRepository())
                    .build())
            .userRepository(getUserRepository())
            .bookRepository(getBookRepository())
            .locationRepository(getLocationRepository())
            .rentalRequestValidators(new LinkedList<>(Arrays.asList(new RentalRequestAgeValidator())))
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
        Optional<RentableState> state = getRentableRepository().getRentableStateAtLocation(this.knownLocation, this.knownBook);
        assertThat(state.isPresent()).isTrue();
        assertThat(state.get().getStatus()).isNotEqualTo(RentableState.Status.AVAILABLE);
    }

}
