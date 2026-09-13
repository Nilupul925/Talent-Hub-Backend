package com.talenthub.portfolio_service.service;

import java.util.stream.Collectors;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.talenthub.portfolio_service.dto.PortfolioRequest;
import com.talenthub.portfolio_service.dto.PortfolioResponse;
import com.talenthub.portfolio_service.entity.Portfolio;
import com.talenthub.portfolio_service.entity.Project;
import com.talenthub.portfolio_service.entity.ProjectImage;
import com.talenthub.portfolio_service.exception.PortfolioNotFoundException;
import com.talenthub.portfolio_service.repository.PortfolioRepository;

@Service
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;

    public PortfolioService(PortfolioRepository portfolioRepository) {
        this.portfolioRepository = portfolioRepository;
    }

    @Transactional(readOnly = true)
    public PortfolioResponse get(String email) {
        Portfolio portfolio = portfolioRepository.findByEmail(email)
                .orElseThrow(() -> new PortfolioNotFoundException(email));
        initializeCollections(portfolio);
        return toResponse(portfolio);
    }

    @Transactional
    public PortfolioResponse save(String email, PortfolioRequest request) {
        Portfolio portfolio = portfolioRepository.findByEmail(email).orElseGet(() -> {
            Portfolio newPortfolio = new Portfolio();
            newPortfolio.setEmail(email);
            return newPortfolio;
        });

        portfolio.setContact(request.getContact());
        portfolio.setDescription(request.getDescription());

        portfolio.getProjects().clear();
        request.getProjects().forEach(dto -> {
            Project p = new Project();

            p.setPortfolio(portfolio);
            p.setTitle(dto.getTitle());
            p.setGithubUrl(dto.getGithubUrl());
            p.setDescription(dto.getDescription());

            dto.getImagesUrl().forEach(imgUrl -> {
                ProjectImage i = new ProjectImage();

                i.setProject(p);
                i.setImageUrl(imgUrl.getImageUrl());

                p.getImagesUrl().add(i);
            });

            portfolio.getProjects().add(p);
        });

        Portfolio saved = portfolioRepository.save(portfolio);
        return toResponse(saved);
    }

    @Transactional
    public String delete(String email) {
        Portfolio portfolio = portfolioRepository.findByEmail(email)
                .orElseThrow(() -> new PortfolioNotFoundException(email));

        portfolioRepository.delete(portfolio);

        return email + ": Portfolio delete successful";
    }

    private void initializeCollections(Portfolio portfolio) {
        Hibernate.initialize(portfolio.getProjects());

        for (Project project : portfolio.getProjects()) {
            Hibernate.initialize(project.getImagesUrl());
        }
    }

    private PortfolioResponse toResponse(Portfolio portfolio) {
        return PortfolioResponse.builder()
                .id(portfolio.getId())
                .email(portfolio.getEmail())
                .contact(portfolio.getContact())
                .description(portfolio.getDescription())
                .updatedAt(portfolio.getUpdatedAt())
                .projects(portfolio.getProjects().stream()
                        .map(p -> PortfolioResponse.Project.builder()
                                .id(p.getId())
                                .title(p.getTitle())
                                .description(p.getDescription())
                                .githubUrl(p.getGithubUrl())
                                .imagesUrl(p.getImagesUrl().stream()
                                        .map(img -> PortfolioResponse.ProjectImage.builder()
                                                .id(img.getId())
                                                .imageUrl(img.getImageUrl())
                                                .build())
                                        .collect(Collectors.toList()))
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}
