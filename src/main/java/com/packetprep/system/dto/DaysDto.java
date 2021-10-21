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
    private Long id;
    private String name;
    private String description;
    private Integer numberOfTopics;
}
