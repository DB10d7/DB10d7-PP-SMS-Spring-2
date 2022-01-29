package com.packetprep.system.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FatherAadhar {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "picByte", length = 100000)
    private byte[] picByte;

    //    @JsonIgnore
    @OneToOne(fetch = LAZY,optional = false)
    @JoinTable(name = "fa_user",
            joinColumns = { @JoinColumn(name = "fa_id")},
            inverseJoinColumns = { @JoinColumn (name = "user_id")}
    )
    private User user;
}
