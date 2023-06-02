package com.crashcourse.L8.ProfileData.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "profile")
@Data

public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String address;

    private String email;

    private String mobNo;

    private String roles;

    @OneToOne(mappedBy = "personId",cascade = CascadeType.ALL)
    private AuthRecord authRecord;

    public Profile(Long id, String name, String address, String email, String mobNo, String roles, AuthRecord authRecord) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.email = email;
        this.mobNo = mobNo;
        this.roles = roles;
        this.authRecord = authRecord;
    }

    public Profile(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public Profile() {
    }
}
