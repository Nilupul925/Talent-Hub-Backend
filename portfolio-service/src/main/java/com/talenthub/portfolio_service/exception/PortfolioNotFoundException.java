package com.talenthub.portfolio_service.exception;

public class PortfolioNotFoundException extends RuntimeException {
    public PortfolioNotFoundException(String email) {
        super("No Portfolio found for user email " + email);
    }
}