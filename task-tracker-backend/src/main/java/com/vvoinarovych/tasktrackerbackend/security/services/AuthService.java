package com.vvoinarovych.tasktrackerbackend.security.services;

import com.vvoinarovych.tasktrackerbackend.dto.JwtResponse;
import com.vvoinarovych.tasktrackerbackend.dto.LoginRequest;
import com.vvoinarovych.tasktrackerbackend.dto.SignupRequest;
import com.vvoinarovych.tasktrackerbackend.exception.UserExistsException;
import com.vvoinarovych.tasktrackerbackend.model.ERole;
import com.vvoinarovych.tasktrackerbackend.model.Role;
import com.vvoinarovych.tasktrackerbackend.model.User;
import com.vvoinarovych.tasktrackerbackend.repository.RoleRepository;
import com.vvoinarovych.tasktrackerbackend.repository.UserRepository;
import com.vvoinarovych.tasktrackerbackend.security.jwt.JwtUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final RoleRepository roleRepository;

    public void registerUser(SignupRequest signupRequest) {
        if (existsByUsername(signupRequest)) {
            throw new UserExistsException("Username is already in use");
        }
        if (existsByEmail(signupRequest)) {
            throw new UserExistsException("Email is already in use");
        }
        createUser(signupRequest);
    }

    public JwtResponse authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        return new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles);
    }

    private boolean existsByUsername(SignupRequest signupRequest) {
        return userRepository.existsByUsername(signupRequest.getUsername());
    }

    private boolean existsByEmail(SignupRequest signupRequest) {
        return userRepository.existsByEmail(signupRequest.getEmail());
    }

    private void createUser(SignupRequest signupRequest) {

        User user = new User(signupRequest.getUsername(),
                signupRequest.getEmail(),
                encoder.encode(signupRequest.getPassword()));

        Set<String> strRoles = signupRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                if (role.equals("admin")) {
                    Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(adminRole);
                } else {
                    Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(userRole);
                }
            });
        }
        user.setRoles(roles);
        userRepository.save(user);
    }
}
