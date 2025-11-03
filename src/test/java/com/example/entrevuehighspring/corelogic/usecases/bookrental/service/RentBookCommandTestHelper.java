package com.example.entrevuehighspring.corelogic.usecases.bookrental.service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import com.example.entrevuehighspring.corelogic.usecases.bookrental.model.Book;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.model.Inventory;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.model.Location;

import com.example.entrevuehighspring.corelogic.usecases.bookrental.model.RentalState;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.model.RentalStatus;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.model.Renter;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.persistence.BookRepository;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.persistence.LocationRepository;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.persistence.RenterRepository;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.persistence.inmem.InMemBookRepository;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.persistence.inmem.InMemLocationRepository;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.persistence.inmem.InMemRenterRepository;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.service.listeners.EmailOnRentalGrantedListener;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.service.listeners.RentalListener;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.service.validation.RentalAgeValidator;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.service.validation.RentalValidator;

/**
 * Collection of object builders for quick testing
 */
public class RentBookCommandTestHelper {
    
    /**
     * @param knownBooks
     * @return a BookRepository which knows the books provided.
     */
    public static BookRepository getBookRepository(Book[] knownBooks) {
        Map<UUID, Book> bookCatalog = new HashMap<>();
        Arrays.stream(knownBooks).forEach((Book book) -> bookCatalog.put(book.getId(),book));
        return InMemBookRepository.builder()
          .bookCatalog(bookCatalog)
          .build();
    }

    /**
     * @param knownRenters
     * @return A RenterRepository which knows the renters provided.
     */
    public static RenterRepository getRenterRepository(Renter[] knownRenters) {
        Map<UUID, Renter> renterCatalog = new HashMap<>();
        Arrays.stream(knownRenters).forEach((Renter renter) -> renterCatalog.put(renter.getId(), renter));

        return InMemRenterRepository
          .builder()
          .renters(renterCatalog).build();
    }

    /**
     * @param knownLocations
     * @return LocationRepository with the known locations provided.
     */
    public static LocationRepository getLocationRepository(Location[] knownLocations) {
        Map<UUID, Location> locationCatalog = new HashMap<>();
        Arrays.stream(knownLocations).forEach((Location location) -> locationCatalog.put(location.getId(), location));

        return InMemLocationRepository
          .builder()
          .locations(locationCatalog)
          .build();
    }

    /**
     * @param knownBooks
     * @param knownRenters
     * @param knownLocations
     * @return A RentBookCommandHandler configured with the books, renters, and locations provided
     */
    public static RentBookCommandHandler getRentBookCommandHandler(
        Book[] knownBooks,
        Renter[] knownRenters,
        Location[] knownLocations,
        EmailSender emailSender
    ) {
        return RentBookCommandHandler.builder()
               .bookRepo(getBookRepository(knownBooks))
               .locationRepo(getLocationRepository(knownLocations))
               .renterRepo(getRenterRepository(knownRenters))
               .rentalValidators(new RentalValidator[] {
                    new RentalAgeValidator()
               })
               .rentalListeners(new RentalListener[] {
                    EmailOnRentalGrantedListener.builder()
                    .emailSender(emailSender)
                    .build()
               })
               .build();
    }

    /**
     * @return A simple book
     */
    public static Book getBook() {
        return Book.builder()
            .id(UUID.fromString("00000000-0000-0000-0000-000000000000"))
            .authors(new String[] {"That Famous Author", "That Authors Mentor"})
            .title("That book that never was")
            .minAge(18)
            .build();
    }

    /**
     * @return A simple predictable renter
     */
    public static Renter getRenter() {
        return Renter.builder()
            .id(UUID.fromString("00000000-0000-0000-0000-000000000000"))
            .birthday(LocalDate.of(1985, 1, 1))
            .name("John Tester")
            .build();
    }

    /**
     * @return A second predictable renter distinct from the first.
     */
    public static Renter getGreedyRenter() {
        Renter result = getRenter();
        result.setId(UUID.fromString("99999999-9999-9999-9999-999999999999"));
        return result;
    }

    /**
     * Helper method for adding books to an inventory
     * @param targetMap The inventory map to populate
     * @param bookList the list of books to add
     * @param status The status to give each books
     * @param renter The optoinal renter if the rental status is no Available.
     */
    public static void addBooks(Map<UUID, List<RentalState>> targetMap, Book[] bookList, RentalStatus status, Optional<Renter> renter) {

        for (Book book : bookList) {
            List<RentalState> bookRentalStates = targetMap.get(book.getId());
            if (bookRentalStates == null) {
                bookRentalStates = new LinkedList<>();
                targetMap.put(book.getId(), bookRentalStates);
            }

            bookRentalStates.add(
                RentalState.builder()
                    .status(status)
                    .renter(renter)
                    .build()
            );
        }
    }

    /**
     * @param availableBooks
     * @param unavailableBooks
     * @return Builds a Location with the available books and unavailable books indicated.
     */
    public static Location getLocationWithBooks(Book[] availableBooks, Book[] unavailableBooks) {

        Map<UUID, List<RentalState>> rentalStates = new HashMap<>();

        Renter greedyRenter = getGreedyRenter();

        addBooks(rentalStates, availableBooks , RentalStatus.Available, Optional.empty());
        addBooks(rentalStates, unavailableBooks, RentalStatus.Borrowed, Optional.of(greedyRenter));

        Inventory inventory = Inventory.builder().inventory(rentalStates).build();

        return Location.builder()
            .id(UUID.fromString("00000000-0000-0000-0000-000000000000"))
            .inventory(inventory).build();
    }
}
