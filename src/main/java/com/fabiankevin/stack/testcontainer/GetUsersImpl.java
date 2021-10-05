package com.fabiankevin.stack.testcontainer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetUsersImpl implements GetUsers {
    private final UserRepository userRepository;
    @Override
    public List<UserEntity> execute() {
        return userRepository.findAll();
    }
}
