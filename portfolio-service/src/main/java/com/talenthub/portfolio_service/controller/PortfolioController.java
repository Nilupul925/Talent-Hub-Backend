package com.talenthub.portfolio_service.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.talenthub.portfolio_service.dto.PortfolioRequest;
import com.talenthub.portfolio_service.dto.PortfolioResponse;
import com.talenthub.portfolio_service.service.PortfolioService;


@RestController
@RequestMapping("/api/portfolio")
public class PortfolioController {

    private final PortfolioService portfolioService;

    public PortfolioController(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    @GetMapping()
    public ResponseEntity<PortfolioResponse> getResume(@RequestHeader("X-User") String email) {
        return ResponseEntity.ok(portfolioService.get(email));
    }

    @PutMapping()
    public ResponseEntity<PortfolioResponse> saveResume(
            @RequestHeader("X-User") String email,
            @Valid @RequestBody PortfolioRequest request) {
        return ResponseEntity.ok(portfolioService.save(email, request));
    }

    @DeleteMapping("/delete")
    public String deletePortfolio(@RequestParam("email") String email) {
        return portfolioService.delete(email);
    }

}