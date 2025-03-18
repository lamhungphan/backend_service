package com.course.projectbase.service.Impl;

import com.course.projectbase.controller.request.SignInRequest;
import com.course.projectbase.controller.response.TokenResponse;
import com.course.projectbase.exception.ForBiddenException;
import com.course.projectbase.exception.InvalidDataException;
import com.course.projectbase.model.UserEntity;
import com.course.projectbase.repository.UserRepository;
import com.course.projectbase.service.AuthenticationService;
import com.course.projectbase.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import static com.course.projectbase.common.TokenType.REFRESH_TOKEN;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "AUTHENTICATION-SERVICE")
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public TokenResponse getAccessToken(SignInRequest request) {
        log.info("Get access token");

        try {
            // Thực hiện xác thực với username và password
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

            log.info("isAuthenticated = {}", authenticate.isAuthenticated());
            log.info("Authorities: {}", authenticate.getAuthorities().toString());

            // Nếu xác thực thành công, lưu thông tin vào SecurityContext
            SecurityContextHolder.getContext().setAuthentication(authenticate);
        } catch (BadCredentialsException | DisabledException e) {
            log.error("Login fail, message={}", e.getMessage());
            throw new AccessDeniedException(e.getMessage());
        }
        // Get user
        var user = userRepository.findByUsername(request.getUsername());
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        String accessToken = jwtService.generateAccessToken(user.getId(), request.getUsername(), user.getAuthorities());
        String refreshToken = jwtService.generateRefreshToken(user.getId(), request.getUsername(), user.getAuthorities());

        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public TokenResponse getRefreshToken(String refreshToken) {
        log.info("Get refresh token");

        if (!StringUtils.hasLength(refreshToken)) {
            throw new InvalidDataException("Token must be not blank");
        }

        try {
            // Verify token
            String userName = jwtService.extractUsername(refreshToken, REFRESH_TOKEN);

            // check user is active or inactivated
            UserEntity user = userRepository.findByUsername(userName);

            // generate new access token
            String accessToken = jwtService.generateAccessToken(user.getId(), user.getUsername(), user.getAuthorities());

            return TokenResponse.builder().accessToken(accessToken).refreshToken(refreshToken).build();
        } catch (Exception e) {
            log.error("Access denied! errorMessage: {}", e.getMessage());
            throw new ForBiddenException(e.getMessage());
        }
    }
}
