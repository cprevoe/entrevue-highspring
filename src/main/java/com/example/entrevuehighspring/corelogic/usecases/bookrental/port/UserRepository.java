package com.example.entrevuehighspring.corelogic.usecases.bookrental.port;

import java.util.Optional;

import com.example.entrevuehighspring.domain.User;
import com.example.entrevuehighspring.domain.UserId;

public interface UserRepository {
    Optional<User> getUserById(UserId id);
}
