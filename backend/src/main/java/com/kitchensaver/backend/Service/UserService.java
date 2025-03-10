package com.kitchensaver.backend.Service;

import com.kitchensaver.backend.DTO.LoginRequest;
import com.kitchensaver.backend.DTO.UserRequest;
import com.kitchensaver.backend.DTO.UserResponse;
import com.kitchensaver.backend.Exceptions.EmailAlreadyExistsException;
import com.kitchensaver.backend.Exceptions.InvalidCredentialsException;
import com.kitchensaver.backend.Exceptions.InvalidRequestException;
import com.kitchensaver.backend.model.Role;
import com.kitchensaver.backend.model.Users;
import com.kitchensaver.backend.Repo.UserRepo;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.kitchensaver.backend.util.JwtUtil;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// This class handles user-related tasks
@Service
public class UserService implements UserDetailsService {
    private final UserRepo userRepo; // This helps save and find users in the database
    private final BCryptPasswordEncoder passwordEncoder; // This helps secure passwords
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    // Setting up the service and password encoder
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public UserResponse registerUser(UserRequest request) {
        try {
            // Check if passwords match
            if (!request.getPassword().equals(request.getConfirmPassword())) {
                throw new InvalidRequestException("Passwords do not match!");
            }

            // Check if email already exists in the database
            if (userRepo.findByEmail(request.getEmail()).isPresent()) {
                throw new EmailAlreadyExistsException("Email already exists!");
            }

            // Validate input fields
            if (request.getFirstName() == null || request.getFirstName().isEmpty()) {
                throw new InvalidRequestException("First name is required!");
            }
            if (request.getLastName() == null || request.getLastName().isEmpty()) {
                throw new InvalidRequestException("Last name is required!");
            }
            if (request.getEmail() == null || request.getEmail().isEmpty()) {
                throw new InvalidRequestException("Email is required!");
            }
            if (request.getCell() == null || request.getCell().isEmpty()) {
                throw new InvalidRequestException("Cell number is required!");
            }
            if (request.getOffice() == null || request.getOffice().isEmpty()) {
                throw new InvalidRequestException("Office is required!");
            }
            if (request.getRole() == null || request.getRole().isEmpty()) {
                throw new InvalidRequestException("Role is required!");
            }

            // Create a new user object with the details from the request
            Users user = new Users();
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            user.setEmail(request.getEmail());
            user.setCell(request.getCell());
            user.setOffice(request.getOffice());

            // Assign role (validate input)
            try {
                user.setRole(Role.valueOf(request.getRole().toUpperCase())); // Convert the role to uppercase and set it
            } catch (IllegalArgumentException e) {
                throw new InvalidRequestException("Invalid role! Allowed values: ADMIN, CABINET_MAKER_INSTALLER");
            }

            // Encrypt the password before saving it
            user.setPassword(passwordEncoder.encode(request.getPassword()));

            // Save the new user to the database
            userRepo.save(user);
            String token = JwtUtil.generateToken(user.getEmail(), user.getRole().name());

            // Generate JWT token

            return new UserResponse("User registered successfully", token, user);

        } catch (InvalidRequestException e) {
            return new UserResponse(e.getMessage(), "");
        } catch (EmailAlreadyExistsException e) {
            return new UserResponse(e.getMessage(), "");
        } catch (Exception e) {
            return new UserResponse(e.getMessage(), "");
        }
    }

    public UserResponse updateEmployee(UserRequest request) {
        try {
            if (request.getId() == null || request.getId().isEmpty()) {
                throw new InvalidRequestException("Id is required!");
            }
            Optional<Users> userOptional = userRepo.findById(Long.parseLong(request.getId()));

            // If the user is not found, return a message
            if (userOptional.isEmpty()) {
                throw new InvalidRequestException("User not found!");
            }

            Users user = userOptional.get();
            if (request.getFirstName() != null && !request.getFirstName().isEmpty()) {
                user.setFirstName(request.getFirstName());
            }
            if (request.getLastName() != null && !request.getLastName().isEmpty()) {
                user.setLastName(request.getLastName());
            }
            if (request.getEmail() != null && !request.getEmail().isEmpty()) {
                if (userRepo.findByEmail(request.getEmail()).isPresent() && userRepo.findByEmail(request.getEmail()).get().getId() != user.getId()) {
                    throw new EmailAlreadyExistsException("Email already exists!");
                }
                user.setEmail(request.getEmail());
            }
            if (request.getCell() != null && !request.getCell().isEmpty()) {
                user.setCell(request.getCell());
            }
            if (request.getOffice() != null && !request.getOffice().isEmpty()) {
                user.setOffice(request.getOffice());
            }
            if (request.getRole() != null && !request.getRole().isEmpty()) {
                try {
                    user.setRole(Role.valueOf(request.getRole().toUpperCase())); // Convert the role to uppercase and set it
                } catch (IllegalArgumentException e) {
                    throw new InvalidRequestException("Invalid role! Allowed values: ADMIN, CABINET_MAKER_INSTALLER");
                }
            }

            // Encrypt the password before saving it
            if (request.getPassword() != null && !request.getPassword().isEmpty()) {
                if (!request.getPassword().equals(request.getConfirmPassword())) {
                    throw new InvalidRequestException("Passwords do not match!");
                }
                user.setPassword(passwordEncoder.encode(request.getPassword()));
            }

            // Save the new user to the database
            userRepo.save(user);

            // Generate JWT token

            return new UserResponse("User updated successfully", "", user);

        } catch (InvalidRequestException e) {
            return new UserResponse(e.getMessage(), "");
        } catch (EmailAlreadyExistsException e) {
            return new UserResponse(e.getMessage(), "");
        } catch (Exception e) {
            return new UserResponse(e.getMessage(), "");
        }
    }

    public UserResponse loginUser(LoginRequest request) {
        try {

            // Try to find the user by their email
            Optional<Users> userOptional = userRepo.findByEmail(request.getEmail()); // Find user by email

            // If the user is not found, return a message
            if (userOptional.isEmpty()) {
                throw new InvalidCredentialsException("User not found!");
            }

            // If the user is found, check if the password matches
            Users user = userOptional.get();
            if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                throw new InvalidCredentialsException("Invalid credentials!");
            }
            String token = JwtUtil.generateToken(user.getEmail(), user.getRole().name());

            return new UserResponse("User login successfully", token, user);

        } catch (InvalidCredentialsException e) {

            return new UserResponse(e.getMessage(), "");
        } catch (Exception e) {

            return new UserResponse(e.getMessage(), "");
        }
    }

    public List<Users> getAllEmployees() {
        // Get all users except the admin
        List<Users> users = userRepo.findAllByRoleNot(Role.ADMIN);

        // Map the list of users to user responses
       

        return users;
    }

    public void deleteUser(Long id) {
        userRepo.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users user = userRepo.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        
        return org.springframework.security.core.userdetails.User
            .withUsername(user.getEmail())
            .password(user.getPassword())
            .roles(user.getRole().name())
            .build();
    }

    public UserResponse updateProfile(UserRequest request) {
        try {
            // Find the user by the email
            Optional<Users> userOptional = userRepo.findByEmail(request.getEmail());

            // If the user is not found, return a message
            if (userOptional.isEmpty()) {
                throw new InvalidRequestException("User not found!");
            }

            // Update the user with the new details
            Users user = userOptional.get();
            if (request.getFirstName() != null && !request.getFirstName().isEmpty()) {
                user.setFirstName(request.getFirstName());
            }
            if (request.getLastName() != null && !request.getLastName().isEmpty()) {
                user.setLastName(request.getLastName());
            }
            if (request.getPassword() != null && !request.getPassword().isEmpty()) {
                user.setPassword(passwordEncoder.encode(request.getPassword()));
            }
            if (request.getCell() != null && !request.getCell().isEmpty()) {
                user.setCell(request.getCell());
            }

            // Save the updated user
            userRepo.save(user);

            // Generate a new JWT token

            // Return the user response
            return new UserResponse("User updated successfully", "", user);

        } catch (InvalidRequestException e) {
            return new UserResponse(e.getMessage(), "");
        } catch (Exception e) {
            return new UserResponse(e.getMessage(), "");
        }
    }
}
