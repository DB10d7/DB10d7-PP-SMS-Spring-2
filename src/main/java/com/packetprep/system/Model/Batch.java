package com.packetprep.system.Model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.List;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Batch {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @NotBlank(message = "Community name is required")
    private String name;
    @NotBlank(message = "Description is required")
    private String description;
    @Column
    private Instant createdOn;
    @Column
    private Instant updatedOn;
//    @ManyToOne(fetch = LAZY)
//    @JoinColumn(name = "userId", referencedColumnName = "userId")
//    private User createdBy;
    @OneToMany(fetch = LAZY)
    private List<Day> days;
//    private Instant createdDate;
  /*  @ManyToMany(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "batch_user",
            joinColumns = { @JoinColumn(name = "batch_id")},
            inverseJoinColumns = { @JoinColumn (name = "user_id")}
    )
    private List<User> user; */
}
