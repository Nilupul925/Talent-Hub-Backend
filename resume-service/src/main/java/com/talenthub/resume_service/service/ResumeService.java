package com.talenthub.resume_service.service;

import java.util.stream.Collectors;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.talenthub.resume_service.dto.ResumeRequest;
import com.talenthub.resume_service.dto.ResumeResponse;
import com.talenthub.resume_service.entity.Certificate;
import com.talenthub.resume_service.entity.Education;
import com.talenthub.resume_service.entity.Experience;
import com.talenthub.resume_service.entity.Language;
import com.talenthub.resume_service.entity.Project;
import com.talenthub.resume_service.entity.Resume;
import com.talenthub.resume_service.entity.Skill;
import com.talenthub.resume_service.exception.InvalidResumeDataException;
import com.talenthub.resume_service.exception.ResumeNotFoundException;
import com.talenthub.resume_service.repository.ResumeRepository;

@Service
public class ResumeService {

    private final ResumeRepository resumeRepository;

    public ResumeService(ResumeRepository resumeRepository) {
        this.resumeRepository = resumeRepository;
    }

    @Transactional(readOnly = true)
    public ResumeResponse get(String email) {
        Resume resume = resumeRepository.findByEmail(email)
                .orElseThrow(() -> new ResumeNotFoundException(email));
        initializeCollections(resume);
        return toResponse(resume);
    }

    @Transactional
    public ResumeResponse save(String email, ResumeRequest request) {
        Resume resume = resumeRepository.findByEmail(email).orElseGet(() -> {
            Resume newResume = new Resume();
            newResume.setEmail(email);
            return newResume;
        });

        resume.setFirstName(request.getFirstName());
        resume.setLastName(request.getLastName());
        resume.setAddress(request.getAddress());
        resume.setPhone(request.getPhone());
        resume.setDateOfBirth(request.getDateOfBirth());
        resume.setProfessionalSummary(request.getProfessionalSummary());

        resume.getEducation().clear();
        request.getEducation().forEach(dto -> {
            Education e = new Education();

            e.setResume(resume);
            e.setInstitution(dto.getInstitution());
            e.setQualification(dto.getQualification());
            e.setFieldOfStudy(dto.getFieldOfStudy());
            e.setStartDate(dto.getStartDate());
            e.setEndDate(dto.getEndDate());
            e.setDescription(dto.getDescription());

            resume.getEducation().add(e);
        });

        resume.getExperience().clear();
        request.getExperience().forEach(dto -> {
            Experience x = new Experience();

            x.setResume(resume);
            x.setCompany(dto.getCompany());
            x.setJobTitle(dto.getJobTitle());
            x.setLocation(dto.getLocation());
            x.setStartDate(dto.getStartDate());
            x.setEndDate(dto.getEndDate());
            x.setCurrentlyWorking(dto.isCurrentlyWorking());
            x.setDescription(dto.getDescription());

            resume.getExperience().add(x);
        });

        resume.getSkills().clear();
        request.getSkills().forEach(dto -> {
            Skill s = new Skill();

            s.setResume(resume);
            s.setName(dto.getName());
            if (dto.getLevel() != null && !dto.getLevel().isBlank()) {
                s.setLevel(parseEnum(Skill.ProficiencyLevel.class, dto.getLevel(), "skills.level"));
            }

            resume.getSkills().add(s);
        });

        resume.getProjects().clear();
        request.getProjects().forEach(dto -> {
            Project p = new Project();

            p.setResume(resume);
            p.setTitle(dto.getTitle());
            p.setDescription(dto.getDescription());
            p.setTechnologies(dto.getTechnologies());
            p.setProjectUrl(dto.getProjectUrl());

            resume.getProjects().add(p);
        });

        resume.getLanguages().clear();
        request.getLanguages().forEach(dto -> {
            Language l = new Language();

            l.setResume(resume);
            l.setName(dto.getName());
            if (dto.getFluency() != null && !dto.getFluency().isBlank()) {
                l.setFluency(parseEnum(Language.Fluency.class, dto.getFluency(), "languages.fluency"));
            }

            resume.getLanguages().add(l);
        });

        resume.getCertificates().clear();
        request.getCertificates().forEach(dto -> {
            Certificate c = new Certificate();
            
            c.setResume(resume);
            c.setName(dto.getName());
            c.setIssuingOrganization(dto.getIssuingOrganization());
            c.setIssueDate(dto.getIssueDate());
            c.setCredentialUrl(dto.getCredentialUrl());
            resume.getCertificates().add(c);
        });

        Resume saved = resumeRepository.save(resume);
        return toResponse(saved);
    }

    @Transactional
    public String delete(String email) {
        Resume resume = resumeRepository.findByEmail(email)
                .orElseThrow(() -> new ResumeNotFoundException(email));

        resumeRepository.delete(resume);

        return email + ": Resume delete successful";
    }

    private ResumeResponse toResponse(Resume resume) {
        return ResumeResponse.builder()
                .id(resume.getId())
                .email(resume.getEmail())
                .firstName(resume.getFirstName())
                .lastName(resume.getLastName())
                .phone(resume.getPhone())
                .address(resume.getAddress())
                .dateOfBirth(resume.getDateOfBirth())
                .professionalSummary(resume.getProfessionalSummary())
                .updatedAt(resume.getUpdatedAt())
                .education(resume.getEducation().stream()
                        .map(e -> ResumeResponse.EducationItem.builder()
                                .id(e.getId())
                                .institution(e.getInstitution())
                                .qualification(e.getQualification())
                                .fieldOfStudy(e.getFieldOfStudy())
                                .startDate(e.getStartDate())
                                .endDate(e.getEndDate())
                                .description(e.getDescription())
                                .build())
                        .collect(Collectors.toList()))
                .experience(resume.getExperience().stream()
                        .map(x -> ResumeResponse.ExperienceItem.builder()
                                .id(x.getId())
                                .company(x.getCompany())
                                .jobTitle(x.getJobTitle())
                                .location(x.getLocation())
                                .startDate(x.getStartDate())
                                .endDate(x.getEndDate())
                                .currentlyWorking(x.isCurrentlyWorking())
                                .description(x.getDescription())
                                .build())
                        .collect(Collectors.toList()))
                .skills(resume.getSkills().stream()
                        .map(s -> ResumeResponse.SkillItem.builder()
                                .id(s.getId())
                                .name(s.getName())
                                .level(s.getLevel() != null ? s.getLevel().name() : null)
                                .build())
                        .collect(Collectors.toList()))
                .projects(resume.getProjects().stream()
                        .map(p -> ResumeResponse.ProjectItem.builder()
                                .id(p.getId())
                                .title(p.getTitle())
                                .description(p.getDescription())
                                .technologies(p.getTechnologies())
                                .projectUrl(p.getProjectUrl())
                                .build())
                        .collect(Collectors.toList()))
                .languages(resume.getLanguages().stream()
                        .map(l -> ResumeResponse.LanguageItem.builder()
                                .id(l.getId())
                                .name(l.getName())
                                .fluency(l.getFluency() != null ? l.getFluency().name() : null)
                                .build())
                        .collect(Collectors.toList()))
                .certificates(resume.getCertificates().stream()
                        .map(c -> ResumeResponse.CertificateItem.builder()
                                .id(c.getId())
                                .name(c.getName())
                                .issuingOrganization(c.getIssuingOrganization())
                                .issueDate(c.getIssueDate())
                                .credentialUrl(c.getCredentialUrl())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }

    private <E extends Enum<E>> E parseEnum(Class<E> enumType, String rawValue, String fieldName) {
        try {
            return Enum.valueOf(enumType, rawValue.trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new InvalidResumeDataException(
                    fieldName + ": '" + rawValue + "' is not a valid value. Allowed values: "
                            + java.util.Arrays.toString(enumType.getEnumConstants()));
        }
    }

    private void initializeCollections(Resume resume) {
        Hibernate.initialize(resume.getEducation());
        Hibernate.initialize(resume.getExperience());
        Hibernate.initialize(resume.getSkills());
        Hibernate.initialize(resume.getProjects());
        Hibernate.initialize(resume.getLanguages());
        Hibernate.initialize(resume.getCertificates());
    }
}