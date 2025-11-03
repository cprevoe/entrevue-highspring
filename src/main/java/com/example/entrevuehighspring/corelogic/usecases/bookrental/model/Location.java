package com.example.entrevuehighspring.corelogic.usecases.bookrental.model;

import java.util.UUID;

import com.example.entrevuehighspring.corelogic.usecases.bookrental.service.exception.BookNotAvailableException;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Builder
public class Location {
    private UUID id;

    private Inventory inventory;

    public RentalState rent(Renter renter, Book book) throws BookNotAvailableException {
        return this.inventory.rentBook(book, renter);
    }
}
