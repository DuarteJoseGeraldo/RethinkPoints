package com.example.hogwartsPoints.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", unique = true, nullable = false)
    private  long id;
    @Column(name = "cpf")
    private String cpf;
    @Column(name = "name")
    private String name;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "house_id", referencedColumnName = "id")
    private HouseEntity houseEntity;
    @Column(name = "user_type")
    private String userType;
    @Column(name = "password")
    private String password;
    @Column(name = "points")
    private Float points;
    @Column(name = "last_login")
    private LocalDateTime lastLogin;
//    @Column(name = "last_valid_token")
//    private String lastValidToken;
    @Column(name = "is_active")
    private boolean isActive;
}