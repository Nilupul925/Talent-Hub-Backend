package com.talenthub.resume_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data 
@AllArgsConstructor 
@NoArgsConstructor 
@Table(name = "certificates")
public class Certificate {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id", nullable = false)
    private Resume resume;

    @Column(nullable = false) 
    private String name;

    private String issuingOrganization;

    private LocalDate issueDate;

    private String credentialUrl;
}
