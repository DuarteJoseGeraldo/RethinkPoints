package com.example.hogwartsPoints.entity;

import com.example.hogwartsPoints.dto.enums.LoginType;
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
@Table(name = "access_token")
public class AccessTokenEntity {
    @Id
    @Column(name = "user_identifier")
    private String userIdentifier;
    @Column(name = "token", unique = true)
    private String token;
    @Column(name = "login_type")
    @Enumerated(EnumType.STRING)
    private LoginType loginType;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}