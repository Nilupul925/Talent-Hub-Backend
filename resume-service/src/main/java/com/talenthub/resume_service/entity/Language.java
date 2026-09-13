package com.talenthub.resume_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data 
@AllArgsConstructor 
@NoArgsConstructor 
@Table(name = "languages")
public class Language {

    public enum Fluency { BASIC, CONVERSATIONAL, FLUENT, NATIVE }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id", nullable = false)
    private Resume resume;

    @Column(nullable = false) 
    private String name;

    @Enumerated(EnumType.STRING) 
    private Fluency fluency = Fluency.CONVERSATIONAL;

}
