package com.packetprep.system.dto;
import com.packetprep.system.Model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BatchDto {
    private Long id;
    private String name;
    private String description;
    private Integer numberOfDays;
    private User createdBy;
}
