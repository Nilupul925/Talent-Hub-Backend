package com.talenthub.portfolio_service.dto;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PortfolioRequest {

    @NotBlank(message = "Contact is required")
    private String contact;

    @NotBlank(message = "Description is required")
    private String description;

    private List<@Valid Project> projects = new ArrayList<>();

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Project {

        @NotBlank(message = "Title is required")
        private String title;

        @NotBlank(message = "Description is required")
        private String description;

        @NotBlank(message = "Github link is required")
        private String githubUrl;

        private List<@Valid ProjectImage> imagesUrl = new ArrayList<>();
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProjectImage {

        @NotBlank(message = "Images is required")
        private String imageUrl;

    }

}
