package com.skillmatch;

import java.util.List;

public interface SkillMatchService {

    List<String> getAllSkills();

    List<String> getRecommendedJobs(String email);

    List<String> getSimilarUsers(String email);

    List<String> getJobMatchScores(String email);
}