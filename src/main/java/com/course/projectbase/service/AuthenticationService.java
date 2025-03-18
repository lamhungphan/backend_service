package com.course.projectbase.service;

import com.course.projectbase.controller.request.SignInRequest;
import com.course.projectbase.controller.response.TokenResponse;

public interface AuthenticationService {
    TokenResponse getAccessToken(SignInRequest signInRequest);

    TokenResponse getRefreshToken(String signInRequest);
}
