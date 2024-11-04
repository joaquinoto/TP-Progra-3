package com.example.demo.conexion;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.GraphDatabase;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Neo4jConnectionRunner implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        try (var driver = GraphDatabase.driver("bolt://localhost:7687", 
                AuthTokens.basic("neo4j", "adminadmin"))) {
            driver.verifyConnectivity();
            System.out.println("Connection established.");
        } catch (Exception e) {
            System.err.println("Connection failed: " + e.getMessage());
        }
    }
}
