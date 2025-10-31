package com.example.entrevuehighspring.corelogic.usecases.bookrental.dto;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Builder
public class RentRequestDTO {
    UUID locationId;
    UUID renterId;
    UUID bookId;
}
