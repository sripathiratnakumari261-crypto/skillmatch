package com.skillmatch;

import java.util.List;
import java.util.Map;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Session;
import org.springframework.stereotype.Repository;

@Repository
public class SkillMatchRepository {

    private final Driver driver;

    public SkillMatchRepository(Driver driver) {
        this.driver = driver;
    }

    public List<String> getAllSkills() {

        String query = """
                MATCH (s:Skill)
                RETURN s.name AS name
                ORDER BY name
                """;

        try (Session session = driver.session()) {

            return session.run(query)
                    .list(record -> record.get("name").asString());
        }
    }

    public List<String> getRecommendedJobs(String email) {

        String query = """
                MATCH (u:User {email: $email})-[:HAS_SKILL]->(s:Skill)
                      <-[:REQUIRES]-(j:Job)
                RETURN DISTINCT j.title AS title
                ORDER BY title
                """;

        try (Session session = driver.session()) {

            return session.run(
                    query,
                    Map.of("email", email)
            ).list(record -> record.get("title").asString());
        }
    }

    public List<String> getSimilarUsers(String email) {

        String query = """
                MATCH (u:User {email: $email})-[:HAS_SKILL]->(s:Skill)
                      <-[:HAS_SKILL]-(other:User)
                WHERE other.email <> $email
                RETURN other.name AS name,
                       count(s) AS sharedSkills
                ORDER BY sharedSkills DESC, name
                """;

        try (Session session = driver.session()) {

            return session.run(
                    query,
                    Map.of("email", email)
            ).list(record ->
                    record.get("name").asString()
                    + " - "
                    + record.get("sharedSkills").asLong()
                    + " shared skills"
            );
        }
    }

    public List<String> getJobMatchScores(String email) {

        String query = """
                MATCH (u:User {email: $email})-[:HAS_SKILL]->(s:Skill)
                MATCH (j:Job)-[:REQUIRES]->(s)
                WITH j, count(DISTINCT s) AS matchingSkills
                MATCH (j)-[:REQUIRES]->(required:Skill)
                WITH j, matchingSkills, count(DISTINCT required) AS totalSkills
                RETURN j.title AS title,
                       matchingSkills,
                       totalSkills
                ORDER BY matchingSkills DESC, title
                """;

        try (Session session = driver.session()) {

            return session.run(
                    query,
                    Map.of("email", email)
            ).list(record ->
                    record.get("title").asString()
                    + " - "
                    + record.get("matchingSkills").asLong()
                    + "/"
                    + record.get("totalSkills").asLong()
                    + " skills matched"
            );
        }
    }
}