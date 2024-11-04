package com.example.demo.Repositorio;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.Entidades.Serie;

@Repository
public interface SerieRepository extends Neo4jRepository<Serie, String> {

    @Query("MATCH (p:Personas)-[:ACTORES|DIRECTORES]->(s:Serie) WHERE p.nombre = $nombre RETURN s")
    Serie findSerieByPersonaNombre(String nombre);
}