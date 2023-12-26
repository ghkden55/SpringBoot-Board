package com.example.demo.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(length = 100, nullable = false, unique = true)
    private String userEmail;

    @Column(length = 45, nullable = false)
    private String userName;

    @Column(length = 256)
    private String userPassword;

    @Column(length = 16, nullable = false)
    private String userPhoneNumber;


    @Builder
    public User(Long userId, String userEmail, String userName, String userPassword, String userPhoneNumber) {
        this.userId = userId;
        this.userEmail = userEmail;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userPhoneNumber = userPhoneNumber;
    }


}
