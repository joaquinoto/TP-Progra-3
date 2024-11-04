package com.example.demo.Entidades;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node
public class Personas {
    @Id
    private String nombre;
    private int nacido;

    

    // Constructor, getters y setters
    public Personas(String nombre, int nacido) {
        this.nombre = nombre;
        this.nacido = nacido;
    }

   
public String getNombre() {
    return nombre;
}

public void setNombre(String nombre) {
    this.nombre = nombre;
}

public int getNacido() {
    return nacido;
}

public void setNacido(int nacido) {
    this.nacido = nacido;
}


}
