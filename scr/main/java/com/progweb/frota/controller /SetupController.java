package com.progweb.frota.controller;

import com.progweb.frota.model.User;
import com.progweb.frota.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/setup")
public class SetupController {

    @Autowired
    private UserService userService;

    @PostMapping("/create-admin")
    public ResponseEntity<User> createAdmin(@RequestBody User user) {
        // Check if an admin already exists
        if (userService.getAllUsers().stream().anyMatch(u -> u.getRole().equals("ADMIN"))) {
            return ResponseEntity.badRequest().body(null);
        }

        user.setRole("ADMIN");
        User savedUser = userService.saveUser(user);
        return ResponseEntity.ok(savedUser);
    }
}
