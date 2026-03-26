package com.example.coupleapp.auth.service;

import com.example.coupleapp.auth.dto.AuthTokensResponse;
import com.example.coupleapp.auth.dto.LoginRequest;
import com.example.coupleapp.auth.dto.RefreshRequest;
import com.example.coupleapp.auth.dto.RegisterRequest;
import com.example.coupleapp.common.exception.ApiException;
import com.example.coupleapp.token.service.JwtTokenService;
import com.example.coupleapp.token.service.RefreshTokenService;
import com.example.coupleapp.user.entity.User;
import com.example.coupleapp.user.entity.UserSettings;
import com.example.coupleapp.user.entity.UserStatus;
import com.example.coupleapp.user.repository.UserRepository;
import com.example.coupleapp.user.repository.UserSettingsRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final UserSettingsRepository userSettingsRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;
    private final RefreshTokenService refreshTokenService;

    public AuthService(
            UserRepository userRepository,
            UserSettingsRepository userSettingsRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JwtTokenService jwtTokenService,
            RefreshTokenService refreshTokenService
    ) {
        this.userRepository = userRepository;
        this.userSettingsRepository = userSettingsRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenService = jwtTokenService;
        this.refreshTokenService = refreshTokenService;
    }

    @Transactional
    public AuthTokensResponse register(RegisterRequest request) {
        if (userRepository.existsByEmailIgnoreCase(request.email())) {
            throw new ApiException(HttpStatus.CONFLICT, "Email already used");
        }

        User user = new User();
        user.setEmail(request.email().trim().toLowerCase());
        user.setDisplayName(request.displayName().trim());
        user.setPasswordHash(passwordEncoder.encode(request.password()));
        user.setStatus(UserStatus.ACTIVE);
        User savedUser = userRepository.save(user);

        UserSettings settings = new UserSettings();
        settings.setUserId(savedUser.getId());
        settings.setPushEnabled(true);
        userSettingsRepository.save(settings);

        String access = jwtTokenService.issueAccessToken(savedUser.getId(), savedUser.getEmail());
        String refresh = refreshTokenService.issue(savedUser.getId());
        return new AuthTokensResponse(access, refresh, jwtTokenService.accessTokenExpiresInSeconds());
    }

    @Transactional
    public AuthTokensResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        User user = userRepository.findByEmailIgnoreCase(request.email())
                .orElseThrow(() -> new ApiException(HttpStatus.UNAUTHORIZED, "Invalid credentials"));

        String access = jwtTokenService.issueAccessToken(user.getId(), user.getEmail());
        String refresh = refreshTokenService.issue(user.getId());
        return new AuthTokensResponse(access, refresh, jwtTokenService.accessTokenExpiresInSeconds());
    }

    @Transactional
    public AuthTokensResponse refresh(RefreshRequest request) {
        var existing = refreshTokenService.verifyActive(request.refreshToken());
        refreshTokenService.revokeToken(existing.getToken());

        User user = userRepository.findById(existing.getUserId())
                .orElseThrow(() -> new ApiException(HttpStatus.UNAUTHORIZED, "User not found"));

        String access = jwtTokenService.issueAccessToken(user.getId(), user.getEmail());
        String refresh = refreshTokenService.issue(user.getId());
        return new AuthTokensResponse(access, refresh, jwtTokenService.accessTokenExpiresInSeconds());
    }

    @Transactional
    public void logout(String refreshToken) {
        refreshTokenService.revokeToken(refreshToken);
    }
}
