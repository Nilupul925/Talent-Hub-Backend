package com.talenthub.auth_service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.talenthub.auth_service.config.FeignConfig;

@FeignClient(name = "PORTFOLIO-SERVICE", configuration = FeignConfig.class)
public interface PortfolioInterface {
    @DeleteMapping("/api/portfolio/delete")
    public String deletePortfolio(@RequestParam("email") String email);
}
