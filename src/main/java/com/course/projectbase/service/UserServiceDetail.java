package com.course.projectbase.service;

import com.course.projectbase.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public record UserServiceDetail(UserRepository userRepository) { // java 17

    public UserDetailsService getUserDetailsService() {
        return userRepository::findByUsername;
    }
}
