package com.packetprep.system.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.List;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Day {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long dayId;
    @NotBlank(message = "Day Name cannot be empty or Null")
    private String name;
    @Nullable
    private String url;
    @Nullable
    private String sessionName;
    @Nullable
    @Lob
    private String description;
    @Column
    private Instant createdOn;

    @Column
    private Instant updatedOn;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User createdBy;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "id", referencedColumnName = "id")
    private Batch batch;
    @ManyToMany(fetch = LAZY)
    @JoinTable(name = "day_user",
        joinColumns = { @JoinColumn(name = "day_id")},
        inverseJoinColumns = { @JoinColumn (name = "user_id")}
    )
    private List<User> user;

    @NotBlank
    private String topic;
}
