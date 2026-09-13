package com.talenthub.resume_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data 
@AllArgsConstructor 
@NoArgsConstructor 
@Table(name = "skills")
public class Skill {

    public enum ProficiencyLevel { BEGINNER, INTERMEDIATE, ADVANCED, EXPERT }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id", nullable = false)
    private Resume resume;

    @Column(nullable = false) 
    private String name;

    @Enumerated(EnumType.STRING)
    private ProficiencyLevel level = ProficiencyLevel.INTERMEDIATE;

}
