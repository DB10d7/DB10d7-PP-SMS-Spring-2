package com.packetprep.system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PassportPhotoRequest {
    private String username;
    private MultipartFile selectedFile;
}
