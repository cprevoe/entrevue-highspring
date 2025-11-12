package com.example.entrevuehighspring.domain;

import lombok.Builder;
import lombok.Getter;

@Builder
public class Location {
    @Getter private LocationId id;
}
