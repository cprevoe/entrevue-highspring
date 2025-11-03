package com.example.entrevuehighspring.corelogic.usecases.bookrental.model;

import java.util.UUID;

import com.example.entrevuehighspring.corelogic.usecases.bookrental.service.exception.BookNotAvailableException;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Builder
public class Location {
    private UUID id;
}
