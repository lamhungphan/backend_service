package com.course.projectbase.controller;

import com.course.projectbase.common.Gender;
import com.course.projectbase.controller.request.UserCreationRequest;
import com.course.projectbase.controller.request.UserPasswordRequest;
import com.course.projectbase.controller.request.UserUpdateRequest;
import com.course.projectbase.controller.response.UserPageResponse;
import com.course.projectbase.controller.response.UserResponse;
import com.course.projectbase.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Tag(name = "User Controller")
@Slf4j(topic = "USER-CONTROLLER")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "Get user list", description = "API retrieve user from database")
    @GetMapping("/list")
    public Map<String, Object> getList(@RequestParam(required = false) String keyword,
                                       @RequestParam(required = false) String sort,
                                       @RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size) {
        log.info("Get user list");
        UserPageResponse pageResponse = userService.findAll(keyword, sort, page, size);

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.OK.value());
        result.put("message", "user list");
        result.put("data", pageResponse);

        return result;
    }

    @Operation(summary = "Get user detail", description = "API retrieve user detail by ID from database")
    @GetMapping("/{userId}")
    public Map<String, Object> getUserDetail(@PathVariable Long userId) {
        log.info("Get user detail by ID: {}", userId);
        UserResponse userResponse = userService.findById(userId);

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.OK.value());
        result.put("message", "user");
        result.put("data", userResponse);

        return result;
    }

    @Operation(summary = "Create User", description = "API add new user to database")
    @PostMapping("/add")
    public ResponseEntity<Object> createUser(@RequestBody UserCreationRequest request) {

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.CREATED.value());
        result.put("message", "User created successfully");
        result.put("data", userService.save(request));

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @Operation(summary = "Update User", description = "API update user to database")
    @PutMapping("/update")
    public Map<String, Object> updateUser(@RequestBody UserUpdateRequest request) {
        log.info("Updating user: {}", request);

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.ACCEPTED.value());
        result.put("message", "User updated successfully");
        result.put("data", "");

        return result;
    }

    @Operation(summary = "Change Password", description = "API change password for user to database")
    @PatchMapping("/change-pwd")
    public Map<String, Object> changePassword(@RequestBody UserPasswordRequest request) {
        log.info("Changing password for user: {}", request);

        userService.changePassword(request);

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.NO_CONTENT.value());
        result.put("message", "Password updated successfully");
        result.put("data", "");

        return result;
    }

    @Operation(summary = "Delete user", description = "API activate user from database")
    @DeleteMapping("/delete/{userId}")
    public Map<String, Object> deleteUser(@PathVariable Long userId) {
        log.info("Deleting user: {}", userId);

        userService.deleteById(userId);

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.RESET_CONTENT.value());
        result.put("message", "User deleted successfully");
        result.put("data", "");

        return result;
    }
}
