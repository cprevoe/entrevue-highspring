package com.example.entrevuehighspring.domain;

import lombok.Builder;
import lombok.Getter;

@Builder
public class MockLocation implements Location {
    @Getter private LocationId id;
}
