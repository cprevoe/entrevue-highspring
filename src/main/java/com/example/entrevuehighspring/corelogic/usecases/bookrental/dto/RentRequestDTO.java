package com.example.entrevuehighspring.corelogic.usecases.bookrental.dto;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * A request to rent a copy of a book.
 */
@Getter @Setter @Builder
public class RentRequestDTO {

    /**
     * The ID of the location to rent the copy of the book from
     */
    UUID locationId;

    /**
     * The ID of the renter who wishes to rent the copy of the book
     */
    UUID renterId;

    /**
     * The ID of the book which the renter wishes to rent a copy of.
     */
    UUID bookId;
}
