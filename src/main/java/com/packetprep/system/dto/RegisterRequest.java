package com.packetprep.system.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String email;
    private String username;
    private String name;
    private String password;
    private String batch="DEFAULT";
    private String role = "DEFAULT";

    private String surname;
    private String collegeName;
    private String university;
    private String state;
    private String city;
    private String gender;
    private String yearOfPassing;
    private String tenthMarks;
    private String twelfthMarks;
    private String graduationMarks;
    private String number;
    private String status= "Ongoing";
    private String birthDate;

}
