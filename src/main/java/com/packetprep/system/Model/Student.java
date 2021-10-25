package com.packetprep.system.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.List;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentId;
    @NotBlank
    @Column
    private String studentName;
    @NotBlank
    @Column
    private String fatherName;
    @NotNull
    @Column
    private Long number;
    @NotBlank
    @Column
    private String name;
    @NotNull
    @Column
    private Integer age;
    @NotNull
    @Column
    private Integer passOutYear;
    @Column
    private Instant createdOn;
    @Column
    private Instant updatedOn;
    @Lob
    @NotBlank
    @Column
    @NotEmpty
    private String studentEmail;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "batchId", referencedColumnName = "id")
    private Batch batch;
    @ManyToMany(fetch = LAZY, cascade = CascadeType.ALL, mappedBy = "students")
    private List<Day> day;
}
