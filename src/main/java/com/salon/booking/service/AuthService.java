package com.salon.booking.service;

import com.salon.booking.dto.AuthRequestDTO;
import com.salon.booking.dto.AuthResponseDTO;
import com.salon.booking.dto.RegisterRequestDTO;
import com.salon.booking.entity.Role;
import com.salon.booking.entity.User;
import com.salon.booking.repository.RoleRepository;
import com.salon.booking.repository.UserRepository;
import com.salon.booking.security.CustomUserDetails;
import com.salon.booking.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    public AuthResponseDTO register(RegisterRequestDTO request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            log.warn("Registration failed: Email {} is already registered", request.getEmail());
            throw new RuntimeException("Email is already registered");
        }

        Role customerRole = roleRepository.findByRoleName("CUSTOMER")
                .orElseThrow(() -> new RuntimeException("Role CUSTOMER not found"));

        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword()); // Raw password for presentation
        user.setPhoneNumber(request.getPhoneNumber());
        user.setRole(customerRole);

        User savedUser = userRepository.save(user);
        
        CustomUserDetails userDetails = new CustomUserDetails(savedUser);
        String token = jwtUtils.generateToken(userDetails);

        log.info("User registered successfully with email: {}", savedUser.getEmail());

        return new AuthResponseDTO(
                token,
                savedUser.getId(),
                savedUser.getFullName(),
                savedUser.getEmail(),
                savedUser.getRole().getRoleName()
        );
    }

    public AuthResponseDTO login(AuthRequestDTO request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();
        String token = jwtUtils.generateToken(userDetails);

        log.info("User logged in successfully: {}", user.getEmail());

        return new AuthResponseDTO(
                token,
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getRole().getRoleName()
        );
    }
}
