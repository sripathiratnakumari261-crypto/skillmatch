package com.skillmatch.data;

import java.util.List;
import java.util.Map;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Session;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class SeedDataLoader implements CommandLineRunner {

    private final Driver driver;

    public SeedDataLoader(Driver driver) {
        this.driver = driver;
    }

    @Override
    public void run(String... args) {

        try (Session session = driver.session()) {

            createUsers(session);
            createSkills(session);
            createJobs(session);
            createUserSkills(session);
            createJobSkills(session);

            System.out.println("======================================");
            System.out.println("CognoDB seed data loaded successfully");
            System.out.println("======================================");
        }
    }

    private void createUsers(Session session) {

        String query = """
                UNWIND $users AS user
                MERGE (u:User {email: user.email})
                SET u.name = user.name,
                    u.location = user.location
                """;

        List<Map<String, Object>> users = List.of(
                Map.of(
                        "name", "Ratna",
                        "email", "ratna@example.com",
                        "location", "Tenali"
                ),
                Map.of(
                        "name", "Anjali",
                        "email", "anjali@example.com",
                        "location", "Hyderabad"
                ),
                Map.of(
                        "name", "Kiran",
                        "email", "kiran@example.com",
                        "location", "Bengaluru"
                )
        );

        session.run(query, Map.of("users", users)).consume();
    }

    private void createSkills(Session session) {

        String query = """
                UNWIND $skills AS skill
                MERGE (s:Skill {name: skill.name})
                SET s.category = skill.category
                """;

        List<Map<String, Object>> skills = List.of(
                Map.of(
                        "name", "Java",
                        "category", "Backend"
                ),
                Map.of(
                        "name", "Spring Boot",
                        "category", "Backend"
                ),
                Map.of(
                        "name", "SQL",
                        "category", "Database"
                ),
                Map.of(
                        "name", "React",
                        "category", "Frontend"
                ),
                Map.of(
                        "name", "JavaScript",
                        "category", "Frontend"
                ),
                Map.of(
                        "name", "Git",
                        "category", "Tools"
                ),
                Map.of(
                        "name", "REST API",
                        "category", "Backend"
                )
        );

        session.run(query, Map.of("skills", skills)).consume();
    }

    private void createJobs(Session session) {

        String query = """
                UNWIND $jobs AS job
                MERGE (j:Job {jobId: job.jobId})
                SET j.title = job.title,
                    j.company = job.company,
                    j.location = job.location
                """;

        List<Map<String, Object>> jobs = List.of(
                Map.of(
                        "jobId", "JOB001",
                        "title", "Java Developer",
                        "company", "Tech Solutions",
                        "location", "Hyderabad"
                ),
                Map.of(
                        "jobId", "JOB002",
                        "title", "Spring Boot Developer",
                        "company", "Cloud Systems",
                        "location", "Bengaluru"
                ),
                Map.of(
                        "jobId", "JOB003",
                        "title", "Full Stack Developer",
                        "company", "Digital Works",
                        "location", "Hyderabad"
                ),
                Map.of(
                        "jobId", "JOB004",
                        "title", "Backend Developer",
                        "company", "Innovate Labs",
                        "location", "Pune"
                )
        );

        session.run(query, Map.of("jobs", jobs)).consume();
    }

    private void createUserSkills(Session session) {

        String query = """
                UNWIND $relationships AS rel
                MATCH (u:User {email: rel.email})
                MATCH (s:Skill {name: rel.skill})
                MERGE (u)-[:HAS_SKILL]->(s)
                """;

        List<Map<String, Object>> relationships = List.of(
                Map.of(
                        "email", "ratna@example.com",
                        "skill", "Java"
                ),
                Map.of(
                        "email", "ratna@example.com",
                        "skill", "Spring Boot"
                ),
                Map.of(
                        "email", "ratna@example.com",
                        "skill", "SQL"
                ),
                Map.of(
                        "email", "ratna@example.com",
                        "skill", "REST API"
                ),
                Map.of(
                        "email", "ratna@example.com",
                        "skill", "Git"
                ),

                Map.of(
                        "email", "anjali@example.com",
                        "skill", "JavaScript"
                ),
                Map.of(
                        "email", "anjali@example.com",
                        "skill", "React"
                ),
                Map.of(
                        "email", "anjali@example.com",
                        "skill", "Git"
                ),

                Map.of(
                        "email", "kiran@example.com",
                        "skill", "Java"
                ),
                Map.of(
                        "email", "kiran@example.com",
                        "skill", "SQL"
                ),
                Map.of(
                        "email", "kiran@example.com",
                        "skill", "Spring Boot"
                )
        );

        session.run(
                query,
                Map.of("relationships", relationships)
        ).consume();
    }

    private void createJobSkills(Session session) {

        String query = """
                UNWIND $relationships AS rel
                MATCH (j:Job {jobId: rel.jobId})
                MATCH (s:Skill {name: rel.skill})
                MERGE (j)-[:REQUIRES]->(s)
                """;

        List<Map<String, Object>> relationships = List.of(
                Map.of(
                        "jobId", "JOB001",
                        "skill", "Java"
                ),
                Map.of(
                        "jobId", "JOB001",
                        "skill", "SQL"
                ),
                Map.of(
                        "jobId", "JOB001",
                        "skill", "Git"
                ),

                Map.of(
                        "jobId", "JOB002",
                        "skill", "Java"
                ),
                Map.of(
                        "jobId", "JOB002",
                        "skill", "Spring Boot"
                ),
                Map.of(
                        "jobId", "JOB002",
                        "skill", "REST API"
                ),

                Map.of(
                        "jobId", "JOB003",
                        "skill", "Java"
                ),
                Map.of(
                        "jobId", "JOB003",
                        "skill", "React"
                ),
                Map.of(
                        "jobId", "JOB003",
                        "skill", "JavaScript"
                ),

                Map.of(
                        "jobId", "JOB004",
                        "skill", "Java"
                ),
                Map.of(
                        "jobId", "JOB004",
                        "skill", "Spring Boot"
                ),
                Map.of(
                        "jobId", "JOB004",
                        "skill", "SQL"
                )
        );

        session.run(
                query,
                Map.of("relationships", relationships)
        ).consume();
    }
}