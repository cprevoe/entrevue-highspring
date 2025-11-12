package com.example.entrevuehighspring.corelogic.usecases.bookrental.port;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.example.entrevuehighspring.domain.User;
import com.example.entrevuehighspring.domain.UserId;

public class MockUserRepository implements UserRepository {
    
    Map<UserId, User> userInventory = new HashMap<>();

    public void addUser(User user) {
        this.userInventory.put(user.getId(), user);
    }

    @Override
    public Optional<User> getUserById(UserId id) {
        return Optional.ofNullable(userInventory.get(id));
    }
}
