package com.crashcourse.SpringBootTraining.AccountData.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "auth_record")
@Data
public class AuthRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "person_id")
    private Account personId;
}


