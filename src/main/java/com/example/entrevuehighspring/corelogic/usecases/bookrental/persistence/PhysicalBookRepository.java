package com.example.entrevuehighspring.corelogic.usecases.bookrental.persistence;

import java.util.Optional;

import com.example.entrevuehighspring.corelogic.usecases.bookrental.model.Book;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.model.Location;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.model.PhysicalBook;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.model.Renter;

/**
 * A repository tracking physical books by location and book type.
 */
public interface PhysicalBookRepository {

    /**
     * Attempts to rent the first available physical book of the book indicated.
     * 
     * Note: This is book-keeping, it assumes all rules and validations have been applied
     *       elsewhere.
     * @param location The location where the physical book should be rented from
     * @param book The book of interest
     * @param renter The renter who would like to rent the book
     * @return The updated PhysicalBook which was rented, or Optional.empty() if the rental could not be granted.
     */
    public Optional<PhysicalBook> rentFirstAvailable(Location location, Book book, Renter renter);

    /**
     * Adds a physical book to the PhysicalBookInventory and marks it as Available.
     * @param location The location this physical book will be
     * @param book The book type of the physical book
     * @return A PhysicalBook if one was added successfully, else Optional.empty()
     */
    public Optional<PhysicalBook> addBook(Location location, Book book);

}
