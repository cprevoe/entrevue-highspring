package com.example.entrevuehighspring.domain;

import lombok.experimental.SuperBuilder;

/**
 * The unique ID of a book, which is also a RentableId
 */
@SuperBuilder
public class BookId extends RentableId {
    // Only adds semantic meaning.
}