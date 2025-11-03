package com.example.entrevuehighspring.corelogic.usecases.bookrental.service;

import org.junit.jupiter.api.Test;

import com.example.entrevuehighspring.corelogic.usecases.bookrental.dto.RentRequestDTO;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.model.Book;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.model.Location;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.model.Renter;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.service.exception.BookNotAvailableException;

import static org.junit.jupiter.api.Assertions.*;
import static com.example.entrevuehighspring.corelogic.usecases.bookrental.service.RentBookCommandTestHelper.*;

import java.util.Optional;


class RentBookCommandHandlerTest {

    // Cases Omitted
    // - book does not exist in book repo
    // - renter does not exist in renter repo
    // - location does not exist in location repo
    // - Combinations of the above 3 


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

        //  When a known renter requests the known book

        RentRequestDTO rentRequest = RentRequestDTO.builder()
            .bookId(knownBook.getId())
            .renterId(knownRenter.getId())
            .locationId(locationWithBookUnavailable.getId())
            .build();

        Optional<Exception> exception = Optional.empty();
        try {
            rentBookCommandHandler.rentRequest(rentRequest);
        } catch (NullPointerException npe) {
            npe.printStackTrace();
            exception = Optional.of(npe);
        } catch (Exception e) {
            exception = Optional.of(e);
        }

        // Then an exception was thrown
        assertTrue(exception.isPresent());
        //  and the exception is an instance of BookNotAvailableException
        if (exception.isPresent() && !(exception.get() instanceof BookNotAvailableException)) {
            exception.get().printStackTrace();
        }
        assertInstanceOf(BookNotAvailableException.class, exception.get());

    }

}