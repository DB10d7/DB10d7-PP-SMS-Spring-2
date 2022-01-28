package com.packetprep.system.Model;


import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

public class StudentAadhar {
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
    @JoinTable(name = "sa_user",
            joinColumns = { @JoinColumn(name = "sa_id")},
            inverseJoinColumns = { @JoinColumn (name = "user_id")}
    )
    private User user;
}
