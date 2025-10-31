package com.example.entrevuehighspring.corelogic.usecases.bookrental.model.Location;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import com.example.entrevuehighspring.corelogic.usecases.bookrental.model.Book.Book;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.model.Renter.Renter;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.service.exception.BookNotAvailableException;

import lombok.Builder;

@Builder
public class Inventory {
    // Maps Books --> RentalStates where each RentalState represents one instance of the book.
    @Builder.Default
    private Map<UUID, List<RentalState>> inventory = new HashMap<UUID, List<RentalState>>();

    public RentalState rentBook(Book book, Renter renter) throws BookNotAvailableException {

        List<RentalState> states = Optional.ofNullable(inventory.get(book.getId()))
                                           .orElseThrow(() -> new BookNotAtLocationException());

        RentalState availableState = states.stream()
          .filter((RentalState state) -> { return state.getStatus() == RentalStatus.Available; })
          .findFirst()
          .orElseThrow(() -> new BookNotAvailableException());

        availableState.setStatus(RentalStatus.Borrowed);
        availableState.setRenter(Optional.of(renter));

        return availableState;
    }

}
