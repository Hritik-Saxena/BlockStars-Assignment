package com.blockstars.blockstarsassignment.controller;

import com.blockstars.blockstarsassignment.domain.Commission;
import com.blockstars.blockstarsassignment.domain.User;
import com.blockstars.blockstarsassignment.dto.UserDto;
import com.blockstars.blockstarsassignment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserDto payload) {
        userService.registerUser(payload);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/refer")
    public ResponseEntity<String> referUser(
        @RequestParam(required = true) String referrerId,
        @RequestParam(required = true) String referredEmail,
        @RequestParam(required = true) int level
    ) {
        if (level > 3) {
            return ResponseEntity.badRequest().body("Invalid level. Level cannot be greater than 3.");
        }

        userService.referUser(referrerId, referredEmail, level);
        return ResponseEntity.ok("Referral recorded successfully");
    }


    @GetMapping("/commissions")
    public ResponseEntity<List<Commission>> viewCommissions(@RequestParam String userId) {
        return ResponseEntity.ok(userService.viewCommissions(userId));
    }
}
