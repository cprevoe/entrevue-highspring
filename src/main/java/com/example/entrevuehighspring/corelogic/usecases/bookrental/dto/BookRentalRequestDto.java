package com.example.entrevuehighspring.corelogic.usecases.bookrental.dto;

import com.example.entrevuehighspring.domain.BookId;
import com.example.entrevuehighspring.domain.LocationId;
import com.example.entrevuehighspring.domain.UserId;

import lombok.Builder;
import lombok.Getter;

@Builder
public class BookRentalRequestDto {
    @Getter private UserId userId;
    @Getter private BookId bookId;
    @Getter private LocationId locationId;
}
