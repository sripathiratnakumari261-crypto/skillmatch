package com.skillmatch;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SkillMatchController {

    private final SkillMatchService service;

    public SkillMatchController(SkillMatchService service) {
        this.service = service;
    }

    @GetMapping("/skills")
    public List<String> getAllSkills() {
        return service.getAllSkills();
    }

    @GetMapping("/recommendations")
    public List<String> getRecommendedJobs(
            @RequestParam String email) {

        return service.getRecommendedJobs(email);
    }

    @GetMapping("/similar-users")
    public List<String> getSimilarUsers(
            @RequestParam String email) {

        return service.getSimilarUsers(email);
    }

    @GetMapping("/job-match-scores")
    public List<String> getJobMatchScores(
            @RequestParam String email) {

        return service.getJobMatchScores(email);
    }
}