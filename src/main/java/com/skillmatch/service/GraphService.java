package com.skillmatch.service;

import org.springframework.stereotype.Service;

import com.skillmatch.repository.GraphRepository;

@Service
public class GraphService {

    private final GraphRepository graphRepository;

    public GraphService(GraphRepository graphRepository) {
        this.graphRepository = graphRepository;
    }

    public String testConnection() {
        return graphRepository.testConnection();
    }
}