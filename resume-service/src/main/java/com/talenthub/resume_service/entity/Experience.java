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
@Table(name = "experience")
public class Experience {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id", nullable = false)
    private Resume resume;

    @Column(nullable = false) 
    private String company;

    @Column(nullable = false) 
    private String jobTitle;

    private String location;

    private LocalDate startDate;

    private LocalDate endDate;

    private boolean currentlyWorking;

    @Column(columnDefinition = "TEXT") 
    private String description;

}
