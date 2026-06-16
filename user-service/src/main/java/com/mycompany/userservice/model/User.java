package com.mycompany.userservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;

    private String email;
    private String phone;
    private String role;

    private String password;
    @Column(updatable = false, insertable = true)
    @CreatedDate
    private LocalDateTime createdAt;
    @Column(updatable = true, nullable = false)
    @LastModifiedDate
    private LocalDateTime updatedAt;

}
