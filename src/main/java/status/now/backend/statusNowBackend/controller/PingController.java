package status.now.backend.statusNowBackend.controller;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class PingController {

    @GetMapping("/api/ping")
    public String ping(@AuthenticationPrincipal String principal) {
        // Check if user is authenticated
        if (principal != null) {
            return "Pong! Authenticated user: " + principal;
        } else {
            return "Pong! Not authenticated.";
        }
    }
}
