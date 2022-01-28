package com.packetprep.system.Model;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

public class Agreement {
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
    @JoinTable(name = "agreement_user",
            joinColumns = { @JoinColumn(name = "agreement_id")},
            inverseJoinColumns = { @JoinColumn (name = "user_id")}
    )
    private User user;
}
