package com.example.demo.Controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Services.Neo4jService;


@RestController
public class Neo4j {
    private final Neo4jService neo4jService;

    @Autowired
    public Neo4j(Neo4jService neo4jService) {
        this.neo4jService = neo4jService;
    }

    @GetMapping("/checkConnection")
    public String checkConnection() {
        if (neo4jService.isConnected()) {
            return "Connected to Neo4j";
        } else {
            return "Failed to connect to Neo4j";
        }
    }    
    
}