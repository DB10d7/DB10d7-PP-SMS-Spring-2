package com.packetprep.system.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentResponse {
    private Long id;
    private String username;
    private String name;
    private String email;
    private String batch;
    private String role;

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
    private String status;
    private String birthDate;
    private String graduation;
    private String graduationBranch;

    private String fNumber;
    private String fName;
    private String address;
    private Date jDate;
    private String center;
    private String comment;
    private String uid;
}
