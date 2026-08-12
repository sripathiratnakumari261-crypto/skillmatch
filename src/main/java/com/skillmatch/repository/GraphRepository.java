package com.skillmatch.repository;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Session;
import org.neo4j.driver.Result;
import org.springframework.stereotype.Repository;

@Repository
public class GraphRepository {

    private final Driver driver;

    public GraphRepository(Driver driver) {
        this.driver = driver;
    }

    public String testConnection() {

        try (Session session = driver.session()) {

            Result result = session.run("RETURN 'CognoDB Connected Successfully' AS message");

            return result.single()
                    .get("message")
                    .asString();
        }
    }
}