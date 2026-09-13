package com.talenthub.resume_service.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResumeRequest {

    @NotBlank(message = "firstName is required")
    @Size(max = 100, message = "firstName must be at most 100 characters")
    private String firstName;

    @NotBlank(message = "lastName is required")
    @Size(max = 100, message = "lastName must be at most 100 characters")
    private String lastName;

    private String phone;

    @Size(max = 255, message = "address must be at most 255 characters")
    private String address;

    // Renamed from "birthYear" -> it was a full LocalDate, not a year. @Past prevents future dates.
    @Past(message = "dateOfBirth must be in the past")
    private LocalDate dateOfBirth;

    @Size(max = 2000, message = "professionalSummary must be at most 2000 characters")
    private String professionalSummary;

    private List<@Valid EducationDto> education = new ArrayList<>();

    private List<@Valid ExperienceDto> experience = new ArrayList<>();

    private List<@Valid SkillDto> skills = new ArrayList<>();

    private List<@Valid ProjectDto> projects = new ArrayList<>();

    private List<@Valid LanguageDto> languages = new ArrayList<>();

    private List<@Valid CertificateDto> certificates = new ArrayList<>();

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EducationDto {

        @NotBlank(message = "education.institution is required")
        private String institution;

        // Renamed from "qulified" (typo) -> "qualification"
        @NotBlank(message = "education.qualification is required")
        private String qualification;

        private String fieldOfStudy;

        @NotNull(message = "education.startDate is required")
        private LocalDate startDate;

        private LocalDate endDate;

        @Size(max = 2000, message = "education.description must be at most 2000 characters")
        private String description;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ExperienceDto {

        @NotBlank(message = "experience.company is required")
        private String company;

        @NotBlank(message = "experience.jobTitle is required")
        private String jobTitle;

        private String location;

        @NotNull(message = "experience.startDate is required")
        private LocalDate startDate;

        private LocalDate endDate;

        private boolean currentlyWorking;

        @Size(max = 2000, message = "experience.description must be at most 2000 characters")
        private String description;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SkillDto {

        @NotBlank(message = "skills.name is required")
        private String name;

        private String level;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProjectDto {

        @NotBlank(message = "projects.title is required")
        private String title;

        @Size(max = 2000, message = "projects.description must be at most 2000 characters")
        private String description;

        private String technologies;

        private String projectUrl;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LanguageDto {

        @NotBlank(message = "languages.name is required")
        private String name;

        private String fluency;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CertificateDto {

        @NotBlank(message = "certificates.name is required")
        private String name;

        private String issuingOrganization;

        @Past(message = "certificates.issueDate must be in the past")
        private LocalDate issueDate;

        private String credentialUrl;
    }
}
