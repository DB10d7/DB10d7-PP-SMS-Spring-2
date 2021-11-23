package com.packetprep.system.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentResponse {
    private Long id;
    private String username;
    private String name;
    private String email;
    private String password;
    private String batch;
    private String role;
}
