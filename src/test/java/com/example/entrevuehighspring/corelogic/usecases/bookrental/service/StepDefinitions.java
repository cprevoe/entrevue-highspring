package com.example.entrevuehighspring.corelogic.usecases.bookrental.service;

import static com.example.entrevuehighspring.corelogic.usecases.bookrental.service.RentBookCommandTestHelper.*;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.collections.functors.NotNullPredicate;

import com.example.entrevuehighspring.corelogic.usecases.bookrental.dto.RentRequestDTO;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.dto.RentResponseDTO;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.model.Book.Book;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.model.Location.Location;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.model.Renter.Renter;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.service.exception.BookNotAvailableException;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.service.exception.TooYoungForRentalException;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class StepDefinitions {
    Book knownBook;
    Location knownLocation;
    Renter knownRenter;
    Renter knownSecondRenter;
    RentResponseDTO rentalResponse;

    Optional<Exception> exception = Optional.empty();
    
    @Given("a known book")
    public void aKnownBook() {
        this.knownBook = getBook();
    }

    @Given("a known underaged renter")
    public void aKnownUnderagedRenter() {
        Renter underagedRenter = getRenter();
        underagedRenter.setBirthday(LocalDate.now().minusDays(3));
        this.knownRenter = underagedRenter;
    }

    @Given("a location with the known book available")
    public void locationWithKnownBookAvailable() {
        Location location = getLocationWithBooks(new Book[] { this.knownBook }, new Book[0]);
        this.knownLocation = location;
    }

    @Given("a location with the known book unavailable")
    public void locationWithKnownBookUnavailable() {
        Location location = getLocationWithBooks(new Book[0], new Book[] { this.knownBook });
        this.knownLocation = location;
    }


    private void executeRentalRequestAndCaptureExceptions(Renter renter, Book book, Location location) {
        RentRequestDTO rentRequest = RentRequestDTO.builder()
            .bookId(book.getId())
            .renterId(renter.getId())
            .locationId(location.getId())
            .build();


        Renter knownRenters[] = Arrays.asList(this.knownRenter, this.knownSecondRenter)
          .stream()
          .filter(Objects::nonNull)
          .toArray(Renter[]::new);

        RentBookCommandHandler rentBookCommandHandler = getRentBookCommandHandler(
            new Book[]     { knownBook },
            knownRenters,
            new Location[] { location });
           
        try { 
            this.rentalResponse = rentBookCommandHandler.rentRequest(rentRequest);
        } catch (NullPointerException npe) {
            npe.printStackTrace();
            this.exception = Optional.of(npe);
        } catch (Exception e) {
            this.exception = Optional.of(e);
        }

    }

    @When("the renter requests to rent the book")
    public void renterRequestsToRentBook() {
        executeRentalRequestAndCaptureExceptions(this.knownRenter, this.knownBook, this.knownLocation);
    }

    @Then("an exception is thrown")
    public void exceptionIsThrown() {
        assertTrue(this.exception.isPresent());
    }

    @Then("the exception is of type TooYoungForRentalException")
    public void exceptionIsTooYoungForRentalException() {
        assertInstanceOf(TooYoungForRentalException.class, this.exception.get());
    }

    @Given("a known renter")
    public void aKnownRenter() {
        this.knownRenter = getRenter();
    }
  
    @Given("a known second renter")
    public void aKnownSecondRenter() {
        this.knownSecondRenter = getGreedyRenter();
    }
    
    @When("the second renter requests to rent the book")
    public void secondRenterRequestsRentalOfKnownBook() {
        executeRentalRequestAndCaptureExceptions(this.knownSecondRenter, this.knownBook, this.knownLocation);     
    }
    
    @Then("the exception is of type BookNotAvailableException")
    public void exceptionIsOfTypeBookNotAvailableException() {
        assertInstanceOf(BookNotAvailableException.class, this.exception.get());
    }

    @Then("there was no exception")
    public void there_was_no_exception() {
        assertTrue(this.exception.isEmpty());
    }

    @Then("The book was rented successfully")
    public void the_book_was_rented_successfully() {
        assertTrue(this.rentalResponse.isRentGranted());
    }
}
