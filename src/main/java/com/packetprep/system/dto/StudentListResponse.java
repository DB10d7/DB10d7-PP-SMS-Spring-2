package com.packetprep.system.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentListResponse {

    private Long id;
    private String username;
    private String uname;
    private String name;
    private String email;
    private String batch;
    private String number;
    private Integer daysAttended;
    private Integer totalDays;
}
