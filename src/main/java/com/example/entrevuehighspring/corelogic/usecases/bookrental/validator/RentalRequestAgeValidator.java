package com.example.entrevuehighspring.corelogic.usecases.bookrental.validator;

import com.example.entrevuehighspring.domain.Book;
import com.example.entrevuehighspring.domain.Location;
import com.example.entrevuehighspring.domain.User;

public class RentalRequestAgeValidator implements RentalRequestValidator {
    @Override
    public void validate(User user, Book book, Location location) throws RentalValidationException {
        if (user.getAge() < book.getMinAge()) {
            throw new TooYoungForRentalException();
        }
    }
}
