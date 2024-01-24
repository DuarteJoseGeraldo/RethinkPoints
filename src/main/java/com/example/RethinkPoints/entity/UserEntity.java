package com.example.RethinkPoints.entity;

import com.example.RethinkPoints.dto.enums.Status;
import com.example.RethinkPoints.dto.enums.UserType;
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
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", unique = true, nullable = false)
    private  Long id;
    @Column(name = "cpf")
    private String cpf;
    @Column(name = "name")
    private String name;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hub_id", referencedColumnName = "id", nullable = false)
    private HubEntity hub;
    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "address_id", referencedColumnName = "id", nullable = false)
    private AddressEntity address;
    @Column(name = "user_type")
    @Enumerated(EnumType.STRING)
    private UserType userType;
    @Column(name = "password")
    private String password;
    @Column(name = "points")
    private Float points;
    @Column(name = "last_login")
    private LocalDateTime lastLogin;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;
}