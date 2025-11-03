package com.example.entrevuehighspring.corelogic.usecases.bookrental.persistence.inmem;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.util.Pair;

import com.example.entrevuehighspring.corelogic.usecases.bookrental.model.Book;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.model.Location;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.model.PhysicalBook;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.model.Renter;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.model.PhysicalBook.PhysicalBookStatus;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.persistence.PhysicalBookRepository;

/**
 * In-memory implementation of {@link PhysicalBookRepository}
 * 
 * @see PhysicalBookRepository
 */
@org.springframework.stereotype.Component
public class InMemPhysicalBookRepository implements PhysicalBookRepository {

    /**
     * Map of (location + book id) to physical book.
     */
    private Map<Pair<UUID, UUID>, List<PhysicalBook>> inventory = new HashMap<>();

    /**
     * Developer helper to ensure the key is created in the right order.
     * @param location The location
     * @param book The book
     * @return The key to the inventory to that location + book combo
     */
    private Pair<UUID, UUID> getInvKey(Location location, Book book) {
        return Pair.of(location.getId(), book.getId());
    }

    @Override
    public Optional<PhysicalBook> rentFirstAvailable(Location location, Book book, Renter renter) {
        List<PhysicalBook> allCopiesAtLocation = inventory.get(getInvKey(location, book));
        Optional<PhysicalBook> availableCopy = allCopiesAtLocation.stream()
            .filter((PhysicalBook pb) -> (pb.getStatus() == PhysicalBookStatus.AVAILABLE))
            .findFirst();

        availableCopy.ifPresent((PhysicalBook pb) -> {
            pb.setStatus(PhysicalBookStatus.BORROWED);
            pb.setRentedBy(Optional.of(renter));
        });

        return availableCopy;
    }


    public Optional<PhysicalBook> addBook(Location location, Book book) {
        PhysicalBook physicalBook = PhysicalBook.builder()
                                        .book(book)
                                        .rentedBy(Optional.empty())
                                        .status(PhysicalBookStatus.AVAILABLE)
                                        .build();

        Pair<UUID, UUID> invKey = getInvKey(location, book);
        if (!this.inventory.containsKey(invKey)) {
            this.inventory.put(invKey, new LinkedList<>());
        }

        this.inventory.get(invKey).add(physicalBook);

        return Optional.of(physicalBook);
    }
}
