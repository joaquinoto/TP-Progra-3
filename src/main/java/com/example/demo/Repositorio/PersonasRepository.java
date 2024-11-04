package com.example.demo.Repositorio;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import com.example.demo.Entidades.Personas;

public interface PersonasRepository extends Neo4jRepository<Personas, String> {
}
