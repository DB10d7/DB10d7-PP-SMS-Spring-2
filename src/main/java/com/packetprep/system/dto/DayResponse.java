package com.packetprep.system.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DayResponse {
    private Long id;
    private String dayName;
    private String url;
    private String description;
    private String userName;
    private String batchName;
   // private Integer voteCount;
  //  private Integer commentCount;
   // private String duration;
}
