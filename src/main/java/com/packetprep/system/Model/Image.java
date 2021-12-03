package com.packetprep.system.Model;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import static javax.persistence.FetchType.LAZY;
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Image {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "picByte", length = 1000)
    private byte[] picByte;

//    @JsonIgnore
    @OneToOne(fetch = LAZY,optional = false)
    @JoinTable(name = "image_user",
            joinColumns = { @JoinColumn(name = "image_id")},
            inverseJoinColumns = { @JoinColumn (name = "user_id")}
    )
    private User user;

//    public Image(String originalFilename, String contentType, byte[] compressBytes, User user) {
//        this.name= originalFilename;
//        this.type= contentType;
//        this.picByte= compressBytes;
//        this.user= user;
//    }



}
