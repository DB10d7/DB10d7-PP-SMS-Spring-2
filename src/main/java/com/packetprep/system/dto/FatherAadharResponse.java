package com.packetprep.system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FatherAadharResponse {
    private Long id;
    private String name;
    private String type;
    private byte[] picByte;
}
