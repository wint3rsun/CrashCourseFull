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

    public Profile() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobNo() {
        return mobNo;
    }

    public void setMobNo(String mobNo) {
        this.mobNo = mobNo;
    }

    public AuthRecord getAuthRecord() {
        return authRecord;
    }

    public void setAuthRecord(AuthRecord authRecord) {
        this.authRecord = authRecord;
    }
}
