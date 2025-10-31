package com.example.entrevuehighspring.corelogic.usecases.bookrental.service;

import org.junit.jupiter.api.Test;

import com.example.entrevuehighspring.corelogic.usecases.bookrental.dto.RentRequestDTO;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.model.Book.Book;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.model.Book.BookRepository;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.model.Location.Inventory;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.model.Location.Location;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.model.Location.LocationRepository;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.model.Location.RentalState;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.model.Location.RentalStatus;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.model.Renter.Renter;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.model.Renter.RenterRepository;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.service.exception.BookNotAvailableException;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

class RentBookCommandHandlerTest {

    // Cases Omitted
    // - book does not exist in book repo
    // - renter does not exist in renter repo
    // - location does not exist in location repo
    // - Combinations of the above 3 


    BookRepository getBookRepository(Book[] knownBooks) {
        Map<UUID, Book> bookCatalog = new HashMap<>();
        Arrays.stream(knownBooks).forEach((Book book) -> bookCatalog.put(book.getId(),book));
        return BookRepository.builder()
          .bookCatalogue(bookCatalog)
          .build();
    }

    RenterRepository getRenterRepository(Renter[] knownRenters) {
        Map<UUID, Renter> renterCatalog = new HashMap<>();
        Arrays.stream(knownRenters).forEach((Renter renter) -> renterCatalog.put(renter.getId(), renter));

        return RenterRepository
          .builder()
          .renters(renterCatalog).build();
    }

    LocationRepository getLocationRepository(Location[] knownLocations) {
        Map<UUID, Location> locationCatalog = new HashMap<>();
        Arrays.stream(knownLocations).forEach((Location location) -> locationCatalog.put(location.getId(), location));

        return LocationRepository
          .builder()
          .locations(locationCatalog)
          .build();
    }

    RentBookCommandHandler getRentBookCommandHandler(
        Book[] knownBooks,
        Renter[] knownRenters,
        Location[] knownLocations
    ) {
        return RentBookCommandHandler.builder()
               .bookRepo(getBookRepository(knownBooks))
               .locationRepo(getLocationRepository(knownLocations))
               .renterRepo(getRenterRepository(knownRenters))
               .build();
    }

    Book getBook() {
        return Book.builder()
            .id(UUID.fromString("00000000-0000-0000-0000-000000000000"))
            .authors(new String[] {"That Famous Author", "That Authors Mentor"})
            .title("That book that never was")
            .build();
    }

    Renter getRenter() {
        return Renter.builder()
            .id(UUID.fromString("00000000-0000-0000-0000-000000000000"))
            .birthday(LocalDate.of(1985, 1, 1))
            .name("John Tester")
            .build();
    }

    Renter getGreedyRenter() {
        Renter result = getRenter();
        result.setId(UUID.fromString("99999999-9999-9999-9999-999999999999"));
        return result;
    }

    void addBooks(Map<UUID, List<RentalState>> targetMap, Book[] bookList, RentalStatus status, Optional<Renter> renter) {

        for (Book book : bookList) {
            List<RentalState> bookRentalStates = targetMap.get(book.getId());
            if (bookRentalStates == null) {
                bookRentalStates = new LinkedList<>();
                targetMap.put(book.getId(), bookRentalStates);
            }

            bookRentalStates.add(
                RentalState.builder()
                    .status(status)
                    .renter(renter)
                    .build()
            );
        }
    }

    Location getLocationWithBooks(Book[] availableBooks, Book[] unavailableBooks) {

        Map<UUID, List<RentalState>> rentalStates = new HashMap<>();

        Renter greedyRenter = getGreedyRenter();

        addBooks(rentalStates, availableBooks , RentalStatus.Available, Optional.empty());
        addBooks(rentalStates, unavailableBooks, RentalStatus.Borrowed, Optional.of(greedyRenter));

        Inventory inventory = Inventory.builder().inventory(rentalStates).build();

        return Location.builder()
            .id(UUID.fromString("00000000-0000-0000-0000-000000000000"))
            .inventory(inventory).build();
    }

    // Cases to cover:
    // - book doesn't exist
    // - renter doesn't exist
    // - location doesn't exist
    // - book exists and is not at location
    // - book exists and renter exists and book is not available at location
    // - book exists and renter exists and book is available at location

    @Test
    void testShouldThrowExceptionWhenBookIsNotAvailable() {

        // Given a Known Book
        //   and a known renter
        //   and a location where that known book is present but not available
        //   and a RentBookCommandHandler 

        Book knownBook = getBook();
        Renter knownRenter = getRenter();

        Location locationWithBookUnavailable = getLocationWithBooks(new Book[] {}, new Book[] { knownBook });

        RentBookCommandHandler rentBookCommandHandler = getRentBookCommandHandler(
            new Book[]     { knownBook },
            new Renter[]   { knownRenter },
            new Location[] { locationWithBookUnavailable });

        //  When a known renter requests that book

        RentRequestDTO rentRequest = RentRequestDTO.builder()
            .bookId(knownBook.getId())
            .renterId(knownRenter.getId())
            .build();

        Optional<Exception> exception = Optional.empty();
        try {
            rentBookCommandHandler.rentRequest(rentRequest);
        } catch (Exception e) {
            exception = Optional.of(e);
        }

        // Then an exception was thrown
        assertTrue(exception.isPresent());
        //  and the exceptoin is a BookNotAvailableException
        assertInstanceOf(BookNotAvailableException.class, exception.get());

    }

}