package com.course.projectbase.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class UserPasswordRequest implements Serializable {

    @NotNull(message = "id must not be null")
    private Long id;

    @NotBlank(message = "password must not be blank")
    private String password;

    @NotBlank(message = "password must not be blank")
    private String confirmPassword;
}
