package com.example.entrevuehighspring.corelogic.usecases.bookrental.service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import com.example.entrevuehighspring.corelogic.usecases.bookrental.model.Book.Book;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.model.Book.BookRepository;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.model.Book.InMemBookRepository;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.model.Location.Inventory;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.model.Location.Location;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.model.Location.LocationRepository;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.model.Location.RentalState;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.model.Location.RentalStatus;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.model.Renter.Renter;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.model.Renter.RenterRepository;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.service.validation.RentalAgeValidator;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.service.validation.RentalValidator;

public class RentBookCommandTestHelper {
    
    public static BookRepository getBookRepository(Book[] knownBooks) {
        Map<UUID, Book> bookCatalog = new HashMap<>();
        Arrays.stream(knownBooks).forEach((Book book) -> bookCatalog.put(book.getId(),book));
        return InMemBookRepository.builder()
          .bookCatalogue(bookCatalog)
          .build();
    }

    public static RenterRepository getRenterRepository(Renter[] knownRenters) {
        Map<UUID, Renter> renterCatalog = new HashMap<>();
        Arrays.stream(knownRenters).forEach((Renter renter) -> renterCatalog.put(renter.getId(), renter));

        return RenterRepository
          .builder()
          .renters(renterCatalog).build();
    }

    public static LocationRepository getLocationRepository(Location[] knownLocations) {
        Map<UUID, Location> locationCatalog = new HashMap<>();
        Arrays.stream(knownLocations).forEach((Location location) -> locationCatalog.put(location.getId(), location));

        return LocationRepository
          .builder()
          .locations(locationCatalog)
          .build();
    }

    public static RentBookCommandHandler getRentBookCommandHandler(
        Book[] knownBooks,
        Renter[] knownRenters,
        Location[] knownLocations
    ) {
        return RentBookCommandHandler.builder()
               .bookRepo(getBookRepository(knownBooks))
               .locationRepo(getLocationRepository(knownLocations))
               .renterRepo(getRenterRepository(knownRenters))
               .rentalValidators(new RentalValidator[] {
                    new RentalAgeValidator()
               })
               .build();
    }

    public static Book getBook() {
        return Book.builder()
            .id(UUID.fromString("00000000-0000-0000-0000-000000000000"))
            .authors(new String[] {"That Famous Author", "That Authors Mentor"})
            .title("That book that never was")
            .minAge(18)
            .build();
    }

    public static Renter getRenter() {
        return Renter.builder()
            .id(UUID.fromString("00000000-0000-0000-0000-000000000000"))
            .birthday(LocalDate.of(1985, 1, 1))
            .name("John Tester")
            .build();
    }

    public static Renter getGreedyRenter() {
        Renter result = getRenter();
        result.setId(UUID.fromString("99999999-9999-9999-9999-999999999999"));
        return result;
    }

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
