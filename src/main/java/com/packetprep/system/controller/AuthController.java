package com.packetprep.system.controller;

import com.packetprep.system.dto.*;
import com.packetprep.system.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.AllArgsConstructor;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest) {
        authService.signup(registerRequest);
        return new ResponseEntity<>("User Registration Successful",
                HttpStatus.OK);
    }
    @PostMapping("/signupAdmin")
    public ResponseEntity<String> signupAdmin(@RequestBody RegisterRequest registerRequest) {
        authService.signupAdmin(registerRequest);
        return new ResponseEntity<>("User Registration Successful",
                HttpStatus.OK);
    }
    @PutMapping("/update/{username}")
    public ResponseEntity<String> update(@PathVariable String username, @RequestBody RegisterRequest registerRequest) {
        authService.update(registerRequest, username);
        return new ResponseEntity<>("User Update Successful",
                HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<StudentResponse>> getAllUsers() {
        return ResponseEntity.status(OK)
                .body(authService.showAllUser());
    }
    @DeleteMapping("/delete/id")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        authService.deleteById(id);
        return new ResponseEntity<>("User Successfully Deleted",
                HttpStatus.OK);
    }
    @DeleteMapping("/delete/{username}")
    public ResponseEntity<String> delete(@PathVariable String username) {
        authService.delete(username);
        return new ResponseEntity<>("User Successfully Deleted",
                HttpStatus.OK);
    }
    @GetMapping("accountVerification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token) {
        authService.verifyAccount(token);
        return new ResponseEntity<>("Account Activated Successfully", HttpStatus.OK);
    }
    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }
    @GetMapping("/get/{Username}")
    public StudentResponse getSingleUser(@PathVariable String Username) {
        return authService.getSingleUser(Username);
    }
    @PostMapping("/refresh/token")
    public AuthenticationResponse refreshTokens(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        return authService.refreshToken(refreshTokenRequest);
    }
}
