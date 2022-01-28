package com.packetprep.system.Model;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

public class Resume {
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
    @JoinTable(name = "resume_user",
            joinColumns = { @JoinColumn(name = "resume_id")},
            inverseJoinColumns = { @JoinColumn (name = "user_id")}
    )
    private User user;
}
