package com.packetprep.system.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.Instant;
import java.util.List;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long userId;
    @NotBlank(message = "Username is required")
    private String username;
    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "Password is required")
    private String password;
    @Email
    @NotEmpty(message = "Email is required")
    private String email;
    @ManyToMany(fetch = LAZY, cascade = CascadeType.ALL, mappedBy = "user")
    private List<Role> role;
    @ManyToMany(fetch = LAZY, cascade = CascadeType.ALL, mappedBy = "user")
    private List<Day> day;
    @ManyToMany(fetch = LAZY, cascade = CascadeType.ALL, mappedBy = "user")
    private List<Batch> batch;
    private Instant created;
    private boolean enabled;
}
