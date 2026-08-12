package com.skillmatch;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class SkillMatchServiceImpl implements SkillMatchService {

    private final SkillMatchRepository repository;

    public SkillMatchServiceImpl(SkillMatchRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<String> getAllSkills() {
        return repository.getAllSkills();
    }

    @Override
    public List<String> getRecommendedJobs(String email) {
        return repository.getRecommendedJobs(email);
    }

    @Override
    public List<String> getSimilarUsers(String email) {
        return repository.getSimilarUsers(email);
    }

    @Override
    public List<String> getJobMatchScores(String email) {
        return repository.getJobMatchScores(email);
    }
}