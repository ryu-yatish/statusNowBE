package status.now.backend.statusNowBackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import status.now.backend.statusNowBackend.Constants.UserConstants;
import status.now.backend.statusNowBackend.DTO.UserRegistrationRequest;
import status.now.backend.statusNowBackend.Entity.OrganizationEntity;
import status.now.backend.statusNowBackend.Entity.RoleEntity;
import status.now.backend.statusNowBackend.Entity.User;
import status.now.backend.statusNowBackend.repositories.OrganizationRepository;
import status.now.backend.statusNowBackend.repositories.RoleRepository;
import status.now.backend.statusNowBackend.repositories.UserRepository;
import status.now.backend.statusNowBackend.utils.JwtTokenUtil;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private OrganizationRepository organizationRepository; // Injecting OrganizationRepository

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRegistrationRequest request) {

        if (request.getUsername() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Username is empty");
        }

        // Check if the organization already exists
        if (request.getOrganizationName() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Organization name is empty");
        }

        if (userRepository.findByUsername(request.getUsername()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Username already exists.");
        }

        // Check if the organization already exists
        if (organizationRepository.findByName(request.getOrganizationName()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Organization name already exists.");
        }


        // Create a new organization
        OrganizationEntity organizationEntity = new OrganizationEntity();
        organizationEntity.setName(request.getOrganizationName()); // Get the organization name from the request
        organizationEntity = organizationRepository.save(organizationEntity); // Save the organization

        // Create and save the user
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setOrganizationEntity(organizationEntity); // Set the organization for the user

        RoleEntity editoRrole  = roleRepository.findByName(UserConstants.EDITOR);
        user.setRoleEntity(editoRrole);
        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully with organization " + organizationEntity.getName());

    }


    @PostMapping("/login")
    public String login(@RequestBody User user) {
        User foundUser = userRepository.findByUsername(user.getUsername());
        if (foundUser != null && passwordEncoder.matches(user.getPassword(), foundUser.getPassword())) {
            return jwtTokenUtil.generateToken(foundUser.getUsername()); // Return the JWT token
        }
        return "Invalid credentials";
    }
}
