package com.example.coupleapp.user.service;

import com.example.coupleapp.common.exception.ApiException;
import com.example.coupleapp.user.dto.MeResponse;
import com.example.coupleapp.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public MeResponse me(Long userId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "User not found"));

        return new MeResponse(user.getId(), user.getEmail(), user.getDisplayName(), user.getStatus());
    }
}
