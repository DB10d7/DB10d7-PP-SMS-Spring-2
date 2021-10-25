package com.packetprep.system.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDto {

    private Long studentId;
    private String studentName;
    private String studentEmail;
    private String name;
    private String fatherName;
    private Integer age;
    private Long number;
    private String batchName;
    private String batch;
    private Integer passOutYear;
    private Integer numberOfDays;
}
