package com.talenthub.portfolio_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.talenthub.portfolio_service.entity.Portfolio;

import java.util.Optional;

public interface PortfolioRepository extends JpaRepository<Portfolio, Integer> {
    Optional<Portfolio> findByEmail(String email);
}
