package com.talenthub.portfolio_service.dto;

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
public class PortfolioResponse {

    private Integer id;
    private String email;
    private String contact;
    private String description;
    private LocalDateTime updatedAt;

    private List<Project> projects;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Project {
        private Integer id;
        private String title;
        private String description;
        private String githubUrl;

        private List<ProjectImage> imagesUrl;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProjectImage {
        private Integer id;
        private String imageUrl;
    }

}