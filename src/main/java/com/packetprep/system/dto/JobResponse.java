package com.packetprep.system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobResponse {

    private Long id;
    private String userId;
    private String JobId;
    private String title;
    private Date appliedAt;

}
