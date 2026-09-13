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
@Table(name = "education")
public class Education {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id", nullable = false)
    private Resume resume;
 
    private String institution;

    private String qualification;

    private String fieldOfStudy;

    private LocalDate startDate;

    private LocalDate endDate;

    @Column(columnDefinition = "TEXT") 
    private String description;

}
