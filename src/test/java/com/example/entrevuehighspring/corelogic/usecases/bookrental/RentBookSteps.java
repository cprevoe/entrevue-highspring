package com.example.entrevuehighspring.corelogic.usecases.bookrental;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import com.example.entrevuehighspring.corelogic.usecases.bookrental.RentBookTestContext.RentBookTestContextBuilder;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.validator.BookNotAvailableException;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.validator.TooYoungForRentalException;
import com.example.entrevuehighspring.domain.BookId;
import com.example.entrevuehighspring.domain.LocationId;
import com.example.entrevuehighspring.domain.MockBook;
import com.example.entrevuehighspring.domain.MockLocation;
import com.example.entrevuehighspring.domain.MockUser;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class RentBookSteps {

    
    private RentBookTestContextBuilder rentBookTestContextBuilder = RentBookTestContext.builder();
    private Optional<RentBookTestContext> rentBookTestContext = Optional.empty();

    private RentBookTestContext rentBookTestContext() {
        if (rentBookTestContext.isEmpty()) {
            rentBookTestContext = Optional.of(rentBookTestContextBuilder.build());
        }
        
        return rentBookTestContext.get();
    }

    @Given("un enfant locataire connu")
    public void knownChildRenter() {
        rentBookTestContextBuilder.knownUser(
            MockUser.builder()
                .birthday(LocalDate.now())
                .build());
    }

    @Given("un adult locataire connu")
    public void knownAdultRenter() {
        rentBookTestContextBuilder.knownUser(
            MockUser.builder()
                .birthday(LocalDate.now().minusYears(18))
                .build());
    }

    @And("un location connu")
    public void knownLocation() {
        rentBookTestContextBuilder.knownLocation(
            MockLocation.builder()
                    .id(LocationId.builder().id(UUID.randomUUID()).build())
                    .build());
    }

    @And("un livre connu")
    public void aKnownBook() {
        rentBookTestContextBuilder.knownBook(
            MockBook.builder()
                .id(BookId.builder()
                    .id(UUID.randomUUID())
                    .build())
                .build());
    }

    @And("un livre pour les adults connu")
    public void aBookForAdults() {
        rentBookTestContextBuilder.knownBook(
            MockBook.builder()
                .id(BookId.builder().id(UUID.randomUUID()).build())
                .minAge(18)
                .build()
        );
    }

    @And("le livre est disponible")
    public void bookIsAvailable() {
        rentBookTestContextBuilder.isBookAvailable(true);
    }

    @And("le livre n'est pas disponible")
    public void bookIsNotAvailable() {
        rentBookTestContextBuilder.isBookAvailable(false);
    }

    @When("le locataire demande à louer le livre à l'endroit indiqué")
    public void renterRequestsToRentFromIndicatedLocation() {
        RentBookTestContext ctx = rentBookTestContext();
        ctx.rent(ctx.getKnownUser(), ctx.getKnownBook(), ctx.getKnownLocation());
    }

    @Then("la réponse indique que le demande était accordée")
    public void responseIndicatesThatRequestWasGranted() {
        rentBookTestContext().assertResponseGranted();
    }

    @Then("il y a un exception")
    public void thereIsAnException() {
        rentBookTestContext().assertExceptionExists();
    }

    @Then("l'exception est un BookNotAvailableException")
    public void exceptionHasTypeBookNotAvailableException() {
        rentBookTestContext().assertExceptionInstanceOf(BookNotAvailableException.class);
    }

    @Then("l'exception est un TooYoungForRentalException")
    public void exceptionHasTypeTooYoungForRentalException() {
        rentBookTestContext().assertExceptionInstanceOf(TooYoungForRentalException.class);
    }
    
    @Then("le livre n'est plus disponible")
    public void bookIsNoLongerAvailable() {
        rentBookTestContext().assertKnownBookIsNotAvailableAtKnownLocation();
    }

}
