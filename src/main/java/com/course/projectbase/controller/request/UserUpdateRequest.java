package com.course.projectbase.controller.request;

import com.course.projectbase.common.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@ToString
public class UserUpdateRequest implements Serializable {

    @NotNull(message = "id must not be null")
    private Long id;

    @NotBlank(message = "first name must not be blank")
    private String firstName;

    @NotBlank(message = "last name must not be blank")
    private String lastName;

    private Gender gender;
    private Date birthday;
    private String username;

    @Email(message = "Email invalid")
    private String email;
    private String phone;
    private List<AddressRequest> addresses;
}
