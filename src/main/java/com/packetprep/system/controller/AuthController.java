package com.packetprep.system.controller;

import com.packetprep.system.dto.*;
import com.packetprep.system.service.AuthService;
import com.packetprep.system.service.RefreshTokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.AllArgsConstructor;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest) throws IOException {
        return new ResponseEntity<>(authService.signup(registerRequest),
                HttpStatus.OK);
    }
    @GetMapping("/hello")
    public String Hello(){
        return("Hello");
    }

    @PutMapping("/update/{username}")
    public ResponseEntity<String> update(@RequestBody RegisterRequest registerRequest,@PathVariable String username) {
        authService.update(registerRequest,username);
        return new ResponseEntity<>("User Update Successful",
                HttpStatus.OK);
    }
    @PutMapping("/updateProfile/{username}")
    public ResponseEntity<String> updateProfile(@RequestBody RegisterRequest registerRequest,@PathVariable String username) {
        authService.update(registerRequest,username);
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
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        authService.deleteById(id);
        return new ResponseEntity<>("User Successfully Deleted",
                HttpStatus.OK);
    }
//    @DeleteMapping("/delete/{username}")
//    public ResponseEntity<String> delete(@PathVariable String username) {
//        authService.delete(username);
//        return new ResponseEntity<>("User Successfully Deleted",
//                HttpStatus.OK);
//    }
    @GetMapping("/accountVerification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token) {
        authService.verifyAccount(token);
        return new ResponseEntity<>("Account Activated Successfully", HttpStatus.OK);
    }
    @PostMapping("/resetPassword")
    public ResponseEntity<String> verifyUserForResetPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest) {
        return new ResponseEntity<>(authService.verifyUserForPasswordReset(forgotPasswordRequest), HttpStatus.OK);
    }
    @GetMapping("/forgotPassword/{username}")
    public ResponseEntity<String> forgotPassword(@PathVariable String username) {
        return new ResponseEntity<>(authService.forgotPassword(username),HttpStatus.OK);
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
    @GetMapping("/get/trainersList")
    public ResponseEntity<List<StudentResponse>> getTrainers() {
        return ResponseEntity.status(OK)
                .body(authService.showTrainers());
    }
    @GetMapping("/get/defaultRoleUsers")
    public ResponseEntity<List<StudentResponse>> getDefaultRoleUsers() {
        return ResponseEntity.status(OK)
                .body(authService.getDefaultRoleUsers());
    }
    @GetMapping("/get/unverifiedUsers")
    public ResponseEntity<List<StudentResponse>> getUnverifiedUser() {
        return ResponseEntity.status(OK)
                .body(authService.getUnverifiedUser());
    }
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
        return ResponseEntity.status(OK).body("Logout Successful!!!  Refresh Token Deleted Successfully!!");
    }
    @PostMapping("/uploadExcel")
    public String uploadExcel(@RequestBody String excelFile) {
        System.out.println(excelFile);

        return authService.uploadExcel(excelFile);
    }
}
