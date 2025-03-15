package com.course.projectbase.controller.request;

import com.course.projectbase.common.Gender;
import com.course.projectbase.common.UserType;
import com.course.projectbase.validation.ValidBirthDate;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class UserCreationRequest implements Serializable {

    @NotBlank(message = "first name must not be blank")
    private String firstName;

    @NotBlank(message = "last name must not be blank")
    private String lastName;

    private Gender gender;

    @ValidBirthDate
    private Date birthday;

    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Username only contain string, number, underscore")
    private String username;

    @Email(message = "Email invalid")
    private String email;

    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits ")
    private String phone;

    private UserType type;

    private List<AddressRequest> addresses; // home, office
}
