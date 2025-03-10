package com.course.projectbase.service;

import com.course.projectbase.controller.request.UserCreationRequest;
import com.course.projectbase.controller.request.UserPasswordRequest;
import com.course.projectbase.controller.request.UserUpdateRequest;
import com.course.projectbase.controller.response.UserPageResponse;
import com.course.projectbase.controller.response.UserResponse;

import java.util.List;

public interface UserService {
    UserPageResponse findAll(String keyword, String sort, int page, int size);

    UserResponse findById(Long id);

    UserResponse findByEmail(String email);

    UserResponse findByUsername(String username);

    long save(UserCreationRequest req);

    void update(UserUpdateRequest req);

    void changePassword(UserPasswordRequest req);

    void deleteById(Long id);
}
