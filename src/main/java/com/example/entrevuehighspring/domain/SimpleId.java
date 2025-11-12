package com.example.entrevuehighspring.domain;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * Value class for IDs throughout this system.
 */
@SuperBuilder
abstract public class SimpleId {
    @Getter @Setter
    private UUID id;
}
