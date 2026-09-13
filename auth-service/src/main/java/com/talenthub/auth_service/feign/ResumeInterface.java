package com.talenthub.auth_service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.talenthub.auth_service.config.FeignConfig;


@FeignClient(name = "RESUME-SERVICE", configuration = FeignConfig.class)
public interface ResumeInterface {
    @DeleteMapping("/api/resume/delete")
    public String deleteResume(@RequestParam("email") String email);
}
