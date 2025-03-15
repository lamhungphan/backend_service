package com.course.projectbase.controller.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
public class ApiResponse implements Serializable {
    private int status;
    private String message;
    private transient Object data;

    // persistent means that the object has been saved to the database
    // whereas transient means that it hasn't been saved yet.
}
