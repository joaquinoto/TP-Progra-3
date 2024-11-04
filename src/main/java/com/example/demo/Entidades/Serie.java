package com.example.demo.Entidades;

import java.util.List;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;


@Node
public class Serie {
    @Id
    private String name;
    private String displayName;

    

    // Constructor, getters y setters
    public Serie(String name, String displayName) {
        this.name = name;
        this.displayName = displayName;
    }
    private List<Personas> directores;
    private List<Personas> actores;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Personas> getDirectores() {
        return directores;
    }

    public void setDirectores(List<Personas> directores) {
        this.directores = directores;
    }

    public List<Personas> getActores() {
        return actores;
    }

    public void setActores(List<Personas> actores) {
        this.actores = actores;
    }

}

