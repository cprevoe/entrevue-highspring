package com.example.entrevuehighspring.corelogic.usecases.bookrental.model;

import java.util.Optional;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a physical copy of a book
 */
 @Getter @Setter @Builder
public class PhysicalBook {

    public enum PhysicalBookStatus {
        AVAILABLE,
        BORROWED
    }

    UUID id;
    Book book;
    Optional<Renter> rentedBy;
    PhysicalBookStatus status;
}
