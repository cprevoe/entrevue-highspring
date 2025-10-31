package com.example.entrevuehighspring.corelogic.usecases.bookrental.service;

import static com.example.entrevuehighspring.corelogic.usecases.bookrental.service.RentBookCommandTestHelper.*;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Optional;

import com.example.entrevuehighspring.corelogic.usecases.bookrental.dto.RentRequestDTO;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.model.Book.Book;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.model.Location.Location;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.model.Renter.Renter;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.service.exception.TooYoungForRentalException;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class StepDefinitions {
    Book knownBook;
    Location location;
    Renter knownRenter;
    Optional exception = Optional.empty();
    
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
        Location location = getLocationWithBooks(new Book[] { this.knownBook }, new Book[] {});
        this.location = location;
    }

    @When("the renter requests to rent the book")
    public void renterRequestsToRentBook() {
         RentRequestDTO rentRequest = RentRequestDTO.builder()
            .bookId(knownBook.getId())
            .renterId(knownRenter.getId())
            .locationId(location.getId())
            .build();

        RentBookCommandHandler rentBookCommandHandler = getRentBookCommandHandler(
            new Book[]     { knownBook },
            new Renter[]   { knownRenter },
            new Location[] { location });
           
        try { 
            rentBookCommandHandler.rentRequest(rentRequest);
        } catch (NullPointerException npe) {
            npe.printStackTrace();
            this.exception = Optional.of(npe);
        } catch (Exception e) {
            this.exception = Optional.of(e);
        }
    }

    @Then("an exception is thrown")
    public void exceptionIsThrown() {
        assertTrue(this.exception.isPresent());
    }

    @Then("the exception is of type TooYoungForRentalException")
    public void exceptionIsTooYoungForRentalException() {
        // Write code here that turns the phrase above into concrete actions
        assertInstanceOf(TooYoungForRentalException.class, this.exception.get());
    }

}
