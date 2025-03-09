package com.course.projectbase.service;

import com.course.projectbase.controller.request.UserCreationRequest;
import com.course.projectbase.controller.request.UserPasswordRequest;
import com.course.projectbase.controller.response.UserResponse;

import java.util.List;

public interface UserService {
    List<UserResponse> findAll();

    UserResponse findById(int id);

    UserResponse findByEmail(String email);

    UserResponse findByUsername(String username);

    long save(UserCreationRequest req);

    void update(UserCreationRequest req);

    void changePassword(UserPasswordRequest req);

    void deleteById(int id);
}
