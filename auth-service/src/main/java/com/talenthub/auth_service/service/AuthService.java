package com.talenthub.auth_service.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.talenthub.auth_service.dto.LoginRequest;
import com.talenthub.auth_service.dto.LoginResponse;
import com.talenthub.auth_service.dto.RegisterRequest;
import com.talenthub.auth_service.dto.UserResponse;
import com.talenthub.auth_service.entity.Role;
import com.talenthub.auth_service.entity.User;
import com.talenthub.auth_service.exception.InvalidCredentialsException;
import com.talenthub.auth_service.exception.UserAlreadyExistsException;
import com.talenthub.auth_service.exception.UserNotFoundException;
import com.talenthub.auth_service.feign.ResumeInterface;
import com.talenthub.auth_service.repository.UserRepository;
import com.talenthub.auth_service.security.JwtUtil;

import feign.FeignException;

import java.util.List;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final ResumeInterface resumeInterface;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, ResumeInterface resumeInterface, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.resumeInterface = resumeInterface;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public UserResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("Email is already registered");
        }

        User user = new User(
                request.getFirstName(),
                request.getLastName(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                Role.USER
        );

        User saved = userRepository.save(user);

        return UserResponse.fromEntity(saved);
    }

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid email"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid password");
        }

        String token = jwtUtil.generateToken(user.getId(), user.getEmail(), user.getRole().name());
        return new LoginResponse(token);
    }

    public List<UserResponse> listUsers() {
        return userRepository.findAll().stream()
                .filter(u -> u.getRole() == Role.USER)
                .map(UserResponse::fromEntity)
                .toList();
    }

    public String deleteUser(Integer userId) {
        User existUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + userId));

        userRepository.deleteById(userId);

        String resumeResult;
        try {
            resumeResult = resumeInterface.deleteResume(existUser.getEmail());
        } catch (FeignException.NotFound ex) {
            resumeResult = "No resume existed for this user";
        } catch (FeignException ex) {
            resumeResult = "Warning: Resume deletion failed (" + ex.status() + "): " + ex.getMessage();
        }

        return "User delete successful" + resumeResult;
    }
}
