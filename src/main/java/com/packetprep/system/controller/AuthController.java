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
        return new ResponseEntity<>(authService.signup(registerRequest),
                HttpStatus.OK);
    }
    @PostMapping("/signupAdmin")
    public ResponseEntity<String> signupAdmin(@RequestBody RegisterRequest registerRequest) {
        authService.signupAdmin(registerRequest);
        return new ResponseEntity<>("User Registration Successful",
                HttpStatus.OK);
    }
    @PutMapping("/update/{username}")
    public ResponseEntity<String> update(@RequestBody RegisterRequest registerRequest) {
        authService.update(registerRequest);
        return new ResponseEntity<>("User Update Successful",
                HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<StudentResponse>> getAllUsers() {
        return ResponseEntity.status(OK)
                .body(authService.showAllUser());
    }
    @GetMapping("/get/employees")
    public ResponseEntity<List<StudentResponse>> getAllEmployees() {
        return ResponseEntity.status(OK)
                .body(authService.getAllEmployees());
    }
    @GetMapping("/delete/{id}")
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
    @GetMapping("/accountVerification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token) {
        authService.verifyAccount(token);
        return new ResponseEntity<>("Account Activated Successfully", HttpStatus.OK);
    }
    @PostMapping("/resetPassword")
    public ResponseEntity<String> verifyUserForResetPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest) {
        authService.verifyUserForPasswordReset(forgotPasswordRequest);
        return new ResponseEntity<>("Password Reset Successful", HttpStatus.OK);
    }
    @GetMapping("/forgotPassword/{username}")
    public ResponseEntity<String> forgotPassword(@PathVariable String username) {
        authService.forgotPassword(username);
        return new ResponseEntity<>("Password Reset Applied Successfully", HttpStatus.OK);
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }
    @GetMapping("/get/{Username}")
    public StudentResponse getSingleUser(@PathVariable String Username) {
        return authService.getSingleUser(Username);
    }
    @GetMapping("/get/currentUser")
    public StudentResponse getCurrentUser() {
        return authService.getCurrentUser();
    }
    @PostMapping("/refresh/token")
    public AuthenticationResponse refreshTokens(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        return authService.refreshToken(refreshTokenRequest);
    }
    @GetMapping("/get/officeEmployees")
    public ResponseEntity<List<StudentResponse>> getOfficeMember() {
        return ResponseEntity.status(OK)
                .body(authService.showOfficeEmployee());
    }
    @GetMapping("/get/defaultRoleUsers")
    public ResponseEntity<List<StudentResponse>> getDefaultRoleUsers() {
        return ResponseEntity.status(OK)
                .body(authService.getDefaultRoleUsers());
    }
}
