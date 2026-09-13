package com.talenthub.resume_service.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResumeResponse {

    private Integer id;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private String address;
    private LocalDate dateOfBirth;
    private String professionalSummary;
    private LocalDateTime updatedAt;

    private List<EducationItem> education;
    private List<ExperienceItem> experience;
    private List<SkillItem> skills;
    private List<ProjectItem> projects;
    private List<LanguageItem> languages;
    private List<CertificateItem> certificates;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EducationItem {
        private Integer id;
        private String institution;
        private String qualification;
        private String fieldOfStudy;
        private LocalDate startDate;
        private LocalDate endDate;
        private String description;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ExperienceItem {
        private Integer id;
        private String company;
        private String jobTitle;
        private String location;
        private LocalDate startDate;
        private LocalDate endDate;
        private boolean currentlyWorking;
        private String description;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SkillItem {
        private Integer id;
        private String name;
        private String level;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProjectItem {
        private Integer id;
        private String title;
        private String description;
        private String technologies;
        private String projectUrl;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LanguageItem {
        private Integer id;
        private String name;
        private String fluency;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CertificateItem {
        private Integer id;
        private String name;
        private String issuingOrganization;
        private LocalDate issueDate;
        private String credentialUrl;
    }
}