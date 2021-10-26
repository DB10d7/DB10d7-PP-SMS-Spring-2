package com.packetprep.system.Model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import java.util.List;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long roleId;
    @NotBlank(message = "Role is required")
    private String roleName;
    @OneToMany(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "role_user",
            joinColumns = { @JoinColumn(name = "role_id")},
            inverseJoinColumns = { @JoinColumn (name = "user_id")}
    )
    private List<User> user;
}
