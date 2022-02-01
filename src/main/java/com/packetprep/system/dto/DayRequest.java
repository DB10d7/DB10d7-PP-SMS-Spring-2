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
public class DayRequest {
    private Long dayId;
    private String batchName;
    private String dayName;
    private String url;
    private String description;
    private String createdBy;

    private String sessionName;
    private String topic;
}
