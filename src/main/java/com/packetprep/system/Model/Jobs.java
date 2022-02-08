package com.packetprep.system.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import java.util.Date;
import java.util.List;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Jobs {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @NotBlank(message = "UserId is required")
    private String userId;
    @NotBlank(message = "JobId is required")
    private String jobId;
    @NotBlank(message = "JobTitle is required")
    private String title;
    @NotBlank(message = "JobApplied is required")
    private Date appliedAt;
    @ManyToOne(fetch = LAZY)
    private User user;
}
