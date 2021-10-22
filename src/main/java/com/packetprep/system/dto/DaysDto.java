package com.packetprep.system.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DaysDto {
    private Long dayId;
    private String batchName;
    private String dayName;
    private String url;
    private String description;
}
